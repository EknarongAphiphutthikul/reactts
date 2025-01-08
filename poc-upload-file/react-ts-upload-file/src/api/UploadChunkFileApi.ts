import { AxiosError, AxiosResponse } from "axios";
import { uploadChunkFile as uploadChunkFileApi } from "../api/ClientApi";
import { UploadChunkFileResponse } from "../types/model/model.httpclient";

const chunkSize = 1024 * 1024 * 1; // 1MB
const maxThreadUploadParallel = 5;

export const uploadChunkFile = async (file: File, fileGroupId: string): Promise<UploadChunkFileResponse> => {
    const totalChunks = Math.ceil(file.size / chunkSize);
    const chunks = Array.from({ length: totalChunks }, (_, index) => {
        const start = index * chunkSize;
        const end = Math.min(start + chunkSize, file.size);

        const formData = new FormData();
        formData.append("file", file.slice(start, end));
        formData.append("file_name", file.name);
        formData.append("chunk", (index + 1).toString());
        formData.append("total_chunk", totalChunks.toString());
        return formData;
    });

    if (chunks.length === 0) {
        return Promise.reject("file size is zero");
    }

    console.log("start upload first chunk");
    var firstChunk = chunks[0];
    firstChunk.append("file_group_id", fileGroupId);
    return callUploadChunkFile(firstChunk).then((response: UploadChunkFileResponse) => {
        return Promise.resolve(response);
    }).then((response: UploadChunkFileResponse) => {
        if (totalChunks === 1) {
            return Promise.resolve(response);
        }
        console.log("start upload parallel");
        return uploadChunkFileParallel(chunks, response);
    }).catch((error: any) => {
        return Promise.reject(error);
    });
}

const uploadChunkFileParallel = async (chunks: FormData[], response: UploadChunkFileResponse): Promise<UploadChunkFileResponse> => {
    console.log("uploadChunkFileParallel");
    if (response.data == null || response.data === undefined) {
        return Promise.reject("response data is null or undefined");
    }
    if (response.data.file_id == null || response.data.file_id === undefined) {
        return Promise.reject("response data file_id is null or undefined");
    }
    if (response.data.file_group_id == null || response.data.file_group_id === undefined) {
        return Promise.reject("response data file_group_id is null or undefined");
    }

    const start = 1;
    const end = chunks.length;
    const formDatas = chunks.slice(start, end).map((chunk) => {
        if (response.data) {
            chunk.append("file_id", response.data.file_id);
            chunk.append("file_group_id", response.data.file_group_id);
        }
        return chunk;
    });

    console.log("formDatas size ", formDatas.length);
    if (formDatas.length > 1) {
        let processing: Promise<any>[] = [];
        for (var i = 0; i < formDatas.length - 1; i++) {
            console.log("chunk index ", i);
            processing.push(callUploadChunkFile(formDatas[i]));
            if (processing.length === maxThreadUploadParallel || i === formDatas.length - 2) {
                console.log("start processing parallel");
                let resultError = await Promise.all(processing).then((respo: UploadChunkFileResponse[]) => {
                    return Promise.resolve([]);
                }).catch((error: AxiosError) => { 
                    return Promise.reject(error) 
                });
                console.log("resultError ", resultError);
                if (resultError.length > 0) {
                    return Promise.reject(resultError[0]);
                }
                console.log("done processing parallel");
                processing = [];
            }
        }
    }

    console.log("last chunk");
    const lastChunk = formDatas[formDatas.length - 1];
    return callUploadChunkFile(lastChunk).then((response: UploadChunkFileResponse) => {
        return Promise.resolve(response);
    }).catch((error: AxiosError) => {
        return Promise.reject(error);
    });
}

const callUploadChunkFile = async (formData: FormData): Promise<UploadChunkFileResponse> => {
    return uploadChunkFileApi(formData)
        .then((response: AxiosResponse<UploadChunkFileResponse>) => {
            return Promise.resolve(response.data);
        }).catch((error: AxiosError) => {
            return Promise.reject(error);
        });
}