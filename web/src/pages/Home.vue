<template>
    <add-button @click.stop="add"/>
    <common-header
            :user="user"
            @logout="logout"
            @on-user-login="log"/>
    <log-dialog v-model="showLoginDialog"
                @login="login"/>
    <div class="contents">
        <el-empty
                v-if="!loading&&data.length==0"
                class="empty"
                description="空空如也"/>
        <el-scrollbar
                ref="msgScrollBar"
                style="height: calc(100vh - 100px)"
                @wheel="scroll">
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
                <div v-if="noMore" style="display:flex;align-items: center;justify-content: center;opacity: 0.8;">
                    没有更多了X_X
                </div>
            </div>
        </el-scrollbar>
    </div>
    <add-msg-dialog
            v-model="showAddDialog"
            :user="user"
            @on-add="(v : string) =>onAdd(v)"/>
</template>

<style lang="scss" scoped>

.contents {
  min-width : var(--min-width);
  max-width : var(--max-width);
  margin    : auto;
  position  : relative;
}

.aynu-card {
  width : 100%;
}

.empty:not(:has(.is-checked)) {
  position : absolute;
  margin   : auto;
  width    : 100%;
  filter   : hue-rotate(170deg) brightness(0.9) saturate(3);
  opacity  : 80%;
}
</style>

<script lang="ts" setup>

import {AddButton, AddMsgDialog, AynuCard, AynuCardData, CommonHeader, LogDialog} from "@components";
import {onMounted, reactive, ref} from "vue";
import gsap from "gsap";
import {Callback, ElMessage, ElScrollbar} from "element-plus";
import axios from "axios";
import {Msg, User} from "@types";
import {LS} from "Global";

const loading = ref(true)

const showAddDialog = ref(false)
const showLoginDialog = ref(false)

const msgScrollBar = ref<InstanceType<typeof ElScrollbar>>()

let data = reactive<(AynuCardData | null)[]>([])

const noMore = ref(false)

const loadCount = 2

let user = ref<{
    uid: number | undefined,
    name: string,
}>({
    uid: undefined,
    name: ""
})

function login(u: User) {
    localStorage.setItem(LS.USER_NAME, u.uid.toString())
    user.value = u
    showLoginDialog.value = false
}

function parseDate(date: string): Date {
    //2023-09-13T09:32:02.000+00:00
    let dateReg = /(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}).(\d{3}).(\d{2}):(\d{2})/
    if (dateReg.test(date)) {
        let gs = dateReg.exec(date)!
        let year = +gs[1]
        let month = +gs[2]
        let day = +gs[3]
        let hour = +gs[4]
        let minute = +gs[5]
        let second = +gs[6]
        let millisecond = +gs[7]
        let timezone = +gs[8]
        return new Date(year, month, day, hour + timezone + 8, minute, second, millisecond)
    }
    throw data
}


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

let lastId = undefined as number | undefined

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
            let time = parseDate(item.time)
            let v = {
                img: '/img/avatar.svg',
                ...item,
                time
            };
            setTimeout(() => {
                data[data.length - loadCount + i] = v
            }, 500 + 200 * (i + 1))
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
        }, 500 + 200 * (ms.length + 1))


    }).catch(err => {
        ElMessage.error("获取数据失败: " + err)
    }).finally(() => {
    })
}

function del(i: number) {
    data.splice(i, 1)
}

function log() {
    showLoginDialog.value = true
}

function logout() {
    axios.post('/api/user/logout', `uid=${user.value.uid}`).then((err) => {
        if (err.data.code == 0) {
            user.value.uid = undefined
            user.value.name = ''
            localStorage.removeItem(LS.USER_NAME)
            ElMessage.success("登出成功")
        } else {
            ElMessage.error(err.data.msg)
        }
    }).catch(err => {
        ElMessage.error(err)
    })
}

function add() {
    if (user.value.uid)
        showAddDialog.value = true
    else {
        showLoginDialog.value = true
        ElMessage.warning("请先登录")
    }
}

function onAdd(v: string) {

    axios.post('/api/msg/send', `uid=${user.value.uid}&msg=${v}`).then((res) => {
        if (res.data.code == '0') {
            loading.value = true
        } else {
            ElMessage.error(res.data.msg)
        }
    }).catch(err => {
        ElMessage.error(err)
    })
    data.splice(0, 0, {
        id: 9999,
        uid: user.value?.uid,
        name: user.value?.name,
        time: new Date(),
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
