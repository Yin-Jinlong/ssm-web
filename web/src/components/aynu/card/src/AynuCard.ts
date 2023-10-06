import {Msg} from "@types";

export declare interface Data extends Msg {
    img: string
}

export declare interface Props {
    data?: Data | null,
    onDelete: () => void
}
