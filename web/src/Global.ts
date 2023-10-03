export function isSystemDarkTheme() {
    return window.matchMedia('(prefers-color-scheme : dark)').matches
}

export enum LS {
    USER_NAME = "user",
    DARK = "dark"
}

export function getTheme(): boolean {
    return localStorage.getItem(LS.DARK) != null
}

export function setTheme(dark: boolean) {
    if (dark)
        localStorage.setItem(LS.DARK, "true")
    else
        localStorage.removeItem(LS.DARK)
}