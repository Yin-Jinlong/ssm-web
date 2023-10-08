<template>
    <add-button @click="add"/>
    <log-dialog v-model="showLoginDialog"
                @logon="logon"
                @login="login"/>
    <el-scrollbar
            ref="msgScrollBar"
            style="height: 100vh"
            @wheel="scroll">
        <common-header
                @logout="logout"
                @on-user-login="log"/>
        <div class="contents">
            <el-empty
                    v-if="!loading&&data.length==0"
                    class="empty"
                    description="空空如也"/>
            <div style="overflow-x:hidden ">
                <transition-group
                        :css="false"
                        tag="div"
                        @enter="onEnter"
                        @leave="onLeave"
                        @before-enter="beforeEnter"
                        @before-leave="beforeLeave">
                    <div
                            v-for="(v,i) in data"
                            :key="i"
                            :data-index="i"
                            style="margin: 1em 0;">
                        <aynu-card
                                :data='v'
                                :on-delete="() =>{del(i)} "
                                class="aynu-card"/>
                    </div>
                </transition-group>
                <div v-if="noMore&&data.length"
                     style="display:flex;align-items: center;justify-content: center;opacity: 0.8;">
                    没有更多了X_X
                </div>
            </div>
        </div>
    </el-scrollbar>
    <add-msg-dialog
            v-model="showAddDialog"
            @on-add="(v : string) =>onAdd(v)"/>
</template>

<style lang="scss" scoped>
@use 'style/Home';
</style>

<script lang="ts" setup>

import {AddButton, AddMsgDialog, AynuCard, AynuCardData, CommonHeader, LogDialog} from "@components";
import {onMounted, ref, reactive} from "vue";
import gsap from "gsap";
import {Callback, ElMessage, ElScrollbar} from "element-plus";
import axios from "axios";
import {Msg, User} from "@types";
import {getErrorMessage} from "Global";
import {globalStatuser} from "@util/Statuser.ts";

const loading = ref(true)

const showAddDialog = ref(false)
const showLoginDialog = ref(false)

const msgScrollBar = ref<InstanceType<typeof ElScrollbar>>()

const noMore = ref(false)

const loadCount = 2

const token = globalStatuser.useRef<string | null>('token', null)
const user = globalStatuser.useRef<User | null>('user', null)
let data = reactive<(AynuCardData | null)[]>([])

function catchError(err: any) {
    ElMessage.error(getErrorMessage(err))
}

async function postLogin(logid: string | undefined = undefined, pwd: string | undefined = undefined): Promise<User> {
    let args: string | undefined = undefined
    if (logid && pwd) {
        args = 'logid=' + logid + '&pwd=' + pwd
    }
    return new Promise<User>((resolve: (u: User) => void, reject: (r: any) => void) => {
        axios.post("/api/user/login", args, {
            headers: {
                "Authorization": token.value
            }
        }).then(res => {
            if (res.data.code == '0') {
                let user = res.data.user as User
                token.value = res.headers["authorization"]
                resolve(user)
            } else {
                reject(res.data.msg)
            }
        }).catch(err => {
            reject(err)
        })
    })
}

function login(u: { logid: string, pwd: string }, callback: (ok: boolean) => void) {
    postLogin(u.logid, u.pwd).then((u: User) => {
        user.value = u
        ElMessage.success("登录成功")
        showLoginDialog.value = false
        callback(true)
    }).catch(err => {
        catchError(err)
        callback(false)
    })
}

function postLogon(name: string, pwd: string): Promise<User> {
    return new Promise<User>((resolve, reject) => {
        axios.post("/api/user/logon", `uname=${name}&pwd=${pwd}`).then(res => {
            if (res.data.code == '0') {
                resolve(res.data.user)
            } else {
                reject(res.data.msg)
            }
        }).catch(err => {
            reject(err)
        })
    })
}

function logon(u: { name: string, pwd1: string }, callback: (ok: boolean) => void) {
    postLogon(u.name, u.pwd1).then((u) => {
        user.value = u
        ElMessage.success("登录成功")
        showLoginDialog.value = false
        callback(true)
    }).catch((err) => {
        catchError(err)
        callback(false)
    })
}

onMounted(() => {
    postLogin().then(u => {
        ElMessage.success(u.name + " 欢迎回来！")
        user.value = u
    }).catch((err) => {
        console.error(err)
    })
})

function scroll(e: WheelEvent): void {
    if (e.deltaY < 0)
        return
    let div = msgScrollBar.value?.wrapRef as HTMLDivElement
    let scrollTop = div.scrollTop
    let max = div?.scrollHeight - div.clientHeight ?? 0
    if (scrollTop > max - 5) {
        load()
    }
}

onMounted(() => {
    if (data.length == 0)
        load()
})

let lastId: number | undefined = undefined
/**
 * 至少要等的时间（观看动画）
 */
let minWaitTime = 200
/**
 * 每个卡片加载后等待时长（距离上个卡片）
 */
let cardLoadedWaitTime = 150

function load() {
    if (data.length > 0 && data[data.length - 1] == null)
        return;
    if (noMore.value)
        return;
    for (let i = 0; i < loadCount; i++) {
        data.push(null)
    }
    let args = ''
    if (lastId)
        args += 'lastId=' + lastId + '&'
    axios.get(`/api/msg/get?${args}count=${loadCount}`).then(res => {
        let ms = res.data.data as Msg[]
        for (let i = 0; i < ms.length; i++) {
            let item = ms[i]
            let v = {
                img: '/img/avatar.svg',
                ...item,
            };
            setTimeout(() => {
                data[data.length - loadCount + i] = v
            }, minWaitTime + cardLoadedWaitTime * (i + 1))
            if (v.id < (lastId ?? Number.MAX_VALUE)) {
                lastId = v.id
            }
        }
        setTimeout(() => {
            loading.value = false
            if (ms.length < loadCount) {
                noMore.value = true
                let c = loadCount - ms.length
                data.splice(data.length - c, c)
            }
            let div = msgScrollBar.value?.wrapRef as HTMLDivElement
            if (div.scrollHeight - div.clientHeight < 1)
                load()
        }, minWaitTime + cardLoadedWaitTime * (ms.length + 1))


    }).catch(err => {
        ElMessage.error("获取数据失败: " + getErrorMessage(err))
    })
}

function del(i: number) {
    // data.splice(i, 1)
}

function log() {
    showLoginDialog.value = true
}

function logout() {
    if (!user.value)
        return
    axios.post('/api/user/logout', `uid=${user.value.uid}`).then((err) => {
        if (err.data.code == 0) {
            user.value = null
            token.value = null
            ElMessage.success("登出成功")
        } else {
            ElMessage.error(err.data.msg)
        }
    }).catch(err => {
        ElMessage.error(getErrorMessage(err))
    })
}

function add() {
    if (user.value)
        showAddDialog.value = true
    else {
        showLoginDialog.value = true
        ElMessage.warning("请先登录")
    }
}

function onAdd(v: string) {
    if (!user.value)
        return
    axios.post('/api/msg/send', `uid=${user.value.uid}&msg=${v}`).then((res) => {
        if (res.data.code == '0') {
            loading.value = true
        } else {
            ElMessage.error(res.data.msg)
        }
    }).catch(err => {
        ElMessage.error(getErrorMessage(err))
    })
    data.splice(0, 0, {
        id: 9999,
        uid: user.value?.uid,
        name: user.value?.name,
        time: Date.now(),
        img: '/img/avatar.svg',
        msg: v
    })
    showAddDialog.value = false
}

function beforeEnter(el: Element) {
    let e = el as HTMLElement
    e.style.opacity = '0'
    e.style.transform = 'translateY(-100px)'
}

function onEnter(el: Element, done: Callback) {
    gsap.to(el, {
        opacity: 1,
        transform: 'translateY(0)',
        delay: 0,
        direction: 0.3,
        onComplete: done
    })
}

function beforeLeave(el: Element) {
    let e = el as HTMLElement
    e.style.transformOrigin = 'top center'
}

function onLeave(el: Element, done: Callback) {
    gsap.to(el, {
        opacity: 0,
        marginTop: '-1em',
        transform: 'scale(0,0)',
        height: '0',
        duration: 0.4,
        onComplete: done
    })
}

</script>
