<template>
    <add-button @click.stop="add"/>
    <el-scrollbar
            ref="scrollBar"
            :always="true"
            :min-size="20"
            height="100vh"
            max-height="100vh"
            style="padding: 0 1em">
        <common-header/>
        <div class="contents">
            <transition-group
                    :css="false"
                    tag="div"
                    @enter="onEnter"
                    @leave="onLeave"
                    @before-enter="beforeEnter"
                    @before-leave="beforeLeave">
                <div
                        v-for="(v,i) in data"
                        :key="v.time.toString()"
                        :data-index="i"
                        style="margin: 1em 0;">
                    <aynu-card
                            :data='v'
                            :on-delete="() =>{del(i)} "
                            class="aynu-card"/>
                </div>
            </transition-group>
        </div>
    </el-scrollbar>
    <add-msg-dialog
            v-model="showAddDialog"
            @on-add="(v : AynuCardData) =>onAdd(v)"/>
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

</style>

<script lang="ts" setup>

import {AddButton, AddMsgDialog, AynuCard, AynuCardData, CommonHeader} from "@components";
import {onMounted, reactive, ref} from "vue";
import gsap from "gsap";
import {Callback, ElMessage, ElScrollbar} from "element-plus";
import axios from "axios";
import User from "@types/User.ts";
import Msg from "@types/Msg.ts";

const showAddDialog = ref(false)

const scrollBar = ref<InstanceType<typeof ElScrollbar>>()

let data = reactive<AynuCardData[]>([])

onMounted(() => {

    function getUser(uid: number, callback: (u: User) => void) {
        axios({
                url: "/api/user/get?uid=" + uid,
                method: 'get'
            }
        ).then(res => {
            callback(res.data.user)
        }).catch(err => {
            ElMessage.error("获取用户信息失败:" + err)
        })
    }

    function parseDate(date: string): Date | null {
        //2023-09-13T09:32:02.000+00:00
        let dateReg = /(\d{4})-(\d{2})-(\d{2})T(\d{2}):(\d{2}):(\d{2}).(\d{3}).(\d{2}):(\d{2})/
        if (dateReg.test(date)) {
            let gs = dateReg.exec(date)!
            let year = +gs[1]
            let month = +gs[2]
            let day = +[3]
            let hour = +gs[4]
            let minute = +gs[5]
            let second = +gs[6]
            let millisecond = +gs[7]
            let timezone = +gs[8]
            return new Date(year, month, day, hour + timezone + 8, minute, second, millisecond)
        }
        return null
    }

    function initAdd() {
        axios.get('/api/msg/all').then(res => {
            console.log(res)
            for (const item of res.data.data as Msg[]) {
                console.log(item)
                getUser(item.uid, (u) => {
                    let time = parseDate(item.time)
                    data.push({
                        ...u,
                        img: '/img/avatar.svg',
                        ...item,
                        time: time
                    })
                })
            }
        }).catch(err => {
            ElMessage.error("获取数据失败: " + err)
        })
    }

    initAdd()
})


function del(i: number) {
    data.splice(i, 1)
}

function add() {
    showAddDialog.value = true
}

function onAdd(v: AynuCardData) {
    data.splice(0, 0, v)
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
        height: '0px',
        duration: 0.4,
        onComplete: done
    })
}

</script>
