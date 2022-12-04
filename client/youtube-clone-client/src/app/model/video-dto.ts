export interface VideoDto {
    id: string,
    title: string,
    description: string,
    videoUrl: string,
    videoStatus: string,
    thumbnailUrl: string,
    tags: string[],
    likesCount: number,
    dislikesCount: number,
    viewsCount: number
}