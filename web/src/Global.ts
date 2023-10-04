export function isSystemDarkTheme() {
    return window.matchMedia('(prefers-color-scheme : dark)').matches
}

export enum LS {
    USER_NAME = "user",
    DARK = "dark",
    AUTO_LOGIN = "autoLogin",
}

export function getTheme(): boolean {
    return localStorage.getItem(LS.DARK) != null
}

function setBoolean(name: string, b: boolean) {
    if (b)
        localStorage.setItem(name, "true")
    else
        localStorage.removeItem(name)
}

export function setTheme(dark: boolean) {
    setBoolean(LS.DARK, dark)
}

export function getAutoLogin() {
    return localStorage.getItem(LS.AUTO_LOGIN) != null
}

export function setAutoLogin(autoLogin: boolean) {
    setBoolean(LS.AUTO_LOGIN, autoLogin)
}