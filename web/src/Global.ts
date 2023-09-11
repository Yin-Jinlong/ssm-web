export function isSystemDarkTheme() {
    return window.matchMedia('(prefers-color-scheme : dark)').matches
}