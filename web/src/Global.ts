import {AxiosError} from "axios";

export function isSystemDarkTheme() {
    return window.matchMedia('(prefers-color-scheme : dark)').matches
}

export enum LS {
    USER_NAME = "user",
}


export function getErrorMessage(err: any): string {
    if (err instanceof AxiosError) {
        let msg = err?.response?.data?.msg;
        if (msg) {
            return msg
        }
    }
    return err.toString()
}