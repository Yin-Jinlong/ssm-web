import {reactive, Ref, ref, UnwrapRef} from "vue";

type VueRef<T> = Ref<UnwrapRef<T>>

export declare interface Statuser {
    readonly name: string

    /**
     * 使用Ref，如果存在则直接返回，不存在返回新的
     * @param name 名字
     * @param def 默认值
     */
    useRef<T>(name: string, def: T): VueRef<T>

    /**
     * 使用Reactive，如果存在则直接返回，不存在返回新的
     * @param name 名字
     * @param def 默认值
     */
    useReactive<T extends Object>(name: string, def: T): T

    load(): void

    save(): void
}

export function useStatuser(name: string): Statuser {
    return new LocalStorageStatuser(name)
}

export function useGlobalStatuser(): Statuser {
    return globalStatuser
}

export function addListeners(statuser: Statuser) {
    window.addEventListener('beforeunload', () => {
        statuser.save()
    })
}

class LocalStorageStatuser implements Statuser {

    readonly name: string;

    private refs: Record<string, VueRef<any>> = {}

    private reactives: Record<string, any> = {}

    private loadedStatuses: Record<string, any> = []

    constructor(name: string) {
        this.name = name
        addListeners(this)
        this.load()
    }

    useRef<T>(name: string, s: T): VueRef<T> {
        let refv = this.refs[name];
        if (refv)
            return refv
        let sv = this.loadedStatuses[name] ?? s
        let v = ref<T>(sv)
        this.refs[name] = v
        return v
    }

    useReactive<T extends Object>(name: string, def: T): T {
        let rv = this.reactives[name]
        if (rv)
            return rv
        let sv = this.loadedStatuses[name] ?? def
        let v = reactive(sv)
        this.reactives[name] = v
        return v
    }

    load() {
        let s = localStorage.getItem("STATUSER_" + this.name)
        if (!s)
            return
        this.loadedStatuses = JSON.parse(s)
    }

    save() {
        let r = {} as Record<string, any>
        forEach(this.refs, (k, v) => {
            r[k] = v.value
        })
        forEach(this.reactives, (k, v) => {
            r[k] = v
        })
        localStorage.setItem("STATUSER_" + this.name, JSON.stringify(r))
    }
}

function forEach<T>(record: Record<string, T>, callback: (k: string, v: T) => void) {
    for (let i in record)
        callback(i, record[i])
}

export const globalStatuser = useStatuser("GLOBAL")