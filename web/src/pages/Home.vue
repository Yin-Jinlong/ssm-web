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
                        :key="v.time"
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
import {Callback, ElScrollbar} from "element-plus";
import axios from "axios";

const showAddDialog = ref(false)

const scrollBar = ref<InstanceType<typeof ElScrollbar>>()

interface KeyedCardData extends AynuCardData {
    time: number
}

let data = reactive<KeyedCardData[]>([])

onMounted(() => {

    function getUser(uid: number, callback) {
        axios({
                url: "/api/user/get?uid="+uid,
                method: 'get'
            }
        ).then(res => {
            callback(res.data.user)
        })
    }

    function initAdd() {
        axios.get('/api/msg/all').then(res => {
            console.log(res)
            for (const item of res.data.data) {
                console.log(item)
                getUser(item.uid, (u) => {
                    data.push({
                        ...u,
                        img: '/img/avatar.svg',
                        ...item
                    })
                    data.sort((a,b)=>{
                        return Date(a)-Date(b)
                    })
                })

            }
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
    data.push({
        ...v,
        time: Date.now()
    })
    showAddDialog.value = false
    let wrapRef = scrollBar.value!.wrapRef as HTMLDivElement

    setTimeout(function scrollBottom() {
        wrapRef.scrollTo({
            behavior: "smooth",
            top: wrapRef.scrollHeight
        })
    }, 16)
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
