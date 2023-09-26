interface Props {
    data: {
        name: string
        img: string
        msg: string
    },
}

declare interface Data {
    uid: number | undefined
    name: string
    img: string
    msg: string
    time: Date
}

export type AynuCardProps = Props

export type AynuCardData = Data