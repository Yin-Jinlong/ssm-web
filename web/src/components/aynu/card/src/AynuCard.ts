export declare interface Data {
    id: number
    uid: number | undefined
    name: string
    img: string
    msg: string
    time: Date
}

export declare interface Props {
    data?: {
        name: string
        img: string
        msg: string
        time: Date
    } | null,
    onDelete: () => void
}
