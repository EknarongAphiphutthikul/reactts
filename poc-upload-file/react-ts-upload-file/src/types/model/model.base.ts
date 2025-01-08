export interface BaseResponse<T = unknown> {
    status: BaseStatus;
    data?: T;
}

export interface BaseStatus {
    code: string | null
    message: string | null
    description: string | null
}