import axios, { AxiosResponse } from "axios";
import {UploadChunkFileResponse} from "../types/model/model.httpclient";

const clientAxios = axios.create();
const urlUpload = "http://localhost:8080/upload-file";


export const uploadChunkFile = async (formData: FormData): Promise<AxiosResponse<UploadChunkFileResponse>> => {
    return clientAxios.post<UploadChunkFileResponse>(urlUpload, formData);
}