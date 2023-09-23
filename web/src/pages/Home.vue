<template>
    <add-button @click.stop="add"/>
    <el-scrollbar
            ref="scrollBar"
            :always="true"
            :min-size="20"
            height="100vh"
            max-height="100vh"
            style="padding: 0 1em">
        <common-header
                :user="user"
                @on-user-login="showLoginDialog=true"/>
        <el-dialog
                v-model="showLoginDialog"
                width="400">
            <template #header>
                <div style="width: 100%;text-align: center"><h3>登录</h3></div>
            </template>
            <template #default>
                <el-form ref="form"
                         :model="logUser"
                         :rules="formRules"
                         label-width="80">
                    <el-form-item label="用户id"
                                  prop="uid"
                                  required>
                        <el-input
                                v-model="logUser.uid"
                                maxlength="12"
                                type="text"/>
                    </el-form-item>
                    <el-form-item label="密码"
                                  prop="pwd"
                                  required>
                        <el-input v-model="logUser.pwd"
                                  :show-password="true"
                                  maxlength="18"
                                  type="password"/>
                    </el-form-item>
                </el-form>
            </template>
            <template #footer>
                <el-button :loading="isLogining"
                           style="width: 100%;height: 4em"
                           type="primary"
                           @click.stop="login"><span>{{ isLogining ? '登陆中...' : '登录' }}</span></el-button>
            </template>
        </el-dialog>
        <div class="contents">
            <el-skeleton
                    :animated="true"
                    :count="3"
                    :loading="loading"
                    style="--el-skeleton-circle-size: 100px">
                <template #template>
                    <div style="display: flex;width: 1000px;margin: 10px auto;align-items: center;justify-content: center">
                        <div style="width: 120px">
                            <el-skeleton-item variant="circle"/>
                            <el-skeleton-item variant="text"/>
                        </div>
                        <div style="width: 100%;padding: 1em">
                            <el-skeleton-item
                                    style="height: 20px;width: 30%;margin: 5px 0"/>
                            <el-skeleton-item
                                    style="height: 60px"/>
                        </div>
                    </div>

                </template>
                <template #default>
                    <el-empty
                            v-if="!loading&&data.length==0"
                            class="empty"
                            description="空空如也"/>
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
                </template>
            </el-skeleton>
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

.empty:not(:has(.is-checked)) {
  position : absolute;
  margin   : auto;
  width    : 100%;
  filter   : hue-rotate(170deg) brightness(0.9) saturate(3);
  opacity  : 80%;
}
</style>

<script lang="ts" setup>

import {AddButton, AddMsgDialog, AynuCard, AynuCardData, CommonHeader} from "@components";
import {onMounted, reactive, ref} from "vue";
import gsap from "gsap";
import {Callback, ElForm, ElMessage, ElScrollbar, FormRules} from "element-plus";
import axios from "axios";
import {Msg} from "@types";

const loading = ref(true)

const showAddDialog = ref(false)
const showLoginDialog = ref(false)

const scrollBar = ref<InstanceType<typeof ElScrollbar>>()

let data = reactive<AynuCardData[]>([])

const form = ref<InstanceType<typeof ElForm>>()

let user = ref<{
    uid: number,
    name: string,
}>()

const isLogining = ref(false)

let logUser = ref<{
    uid: string
    pwd: string
}>({
    uid: '',
    pwd: ''
})

interface FormData {
    name: string,
    pwd: string
}

const formRules = reactive<FormRules<FormData>>({
    name: [
        {required: true, message: '请输入用户名', trigger: 'change'},
        {min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'change'}
    ],
    pwd: [
        {required: true, message: '请输入密码', trigger: 'change'},
        {min: 6, max: 18, message: '密码长度6-18', trigger: 'change'}
    ]
})

function login() {
    form.value!.validate((valid: boolean) => {
        if (valid) {
            isLogining.value = true
            axios.post("/api/user/login", `uid=${logUser.value.uid}&pwd=${logUser.value.pwd}`).then(res => {
                if (res.data.code == '0') {
                    ElMessage.success(res.data.msg)
                    user.value = res.data.user
                    showLoginDialog.value = false
                } else {
                    ElMessage.error(res.data.msg)
                }

            }).catch(err => {
                ElMessage.error(err)
            }).finally(() => {
                setTimeout(() => {
                    isLogining.value = false
                }, 500)

            })
        }
    })

}

onMounted(() => {

    function parseDate(date: string): Date {
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
        throw data
    }

    function initAdd() {
        setTimeout(() => {
            axios.get('/api/msg/all').then(res => {
                console.log(res)
                for (const item of res.data.data as Msg[]) {
                    console.log(item)
                    let time = parseDate(item.time)
                    data.push({
                        img: '/img/avatar.svg',
                        ...item,
                        time
                    })
                }
                loading.value = false
            }).catch(err => {
                ElMessage.error("获取数据失败: " + err)
            })
        }, 1000)
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
