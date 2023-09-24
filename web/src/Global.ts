export function isSystemDarkTheme() {
    return window.matchMedia('(prefers-color-scheme : dark)').matches
}

export enum LS {
    USER_NAME = "user"
}
