import { BaseResponse } from "./model.base";

export interface UploadChunkFileResponse extends BaseResponse<ChunkFileResponse> {}

export interface ChunkFileResponse {
    file_id: string;
    file_group_id: string;
}