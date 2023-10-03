
declare interface Data {
    id: number
    uid: number | undefined
    name: string
    img: string
    msg: string
    time: Date
}

export type AynuCardData = Data