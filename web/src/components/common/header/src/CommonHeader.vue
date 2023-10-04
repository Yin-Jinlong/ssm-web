<template>
    <div class="header bg-dot-blur">
        <div class="header-wrapper">
            <div class="logo">
                <div class="logo-img"/>
                <div class="logo-text">
                    <div class="logo-text-zh">安阳师范学院</div>
                    <div class="logo-text-en">ANYANG NORMAL UNIVERSITY</div>
                </div>
            </div>

            <div class="right-controller">
                <el-dropdown @command="onCommand">
                    <template #default>
                        <div style="cursor: pointer;">
                            <el-avatar
                                    :src="user?.uid?'/img/avatar.svg':''">
                                <svg class="def-avatar" viewBox="0 0 1024 1024" width="200" height="200">
                                    <path d="M501.937582 545.097053c147.891962 0 268.231366-118.534055 268.223274-264.288929 0-145.746783-120.330301-264.28994-268.222263-264.28994-147.890951 0-268.221252 118.584625-268.221252 264.28994C233.71633 426.520519 354.046631 545.097053 501.937582 545.097053zM612.471463 570.546911 411.497184 570.546911c-186.760063 0-338.664249 149.569875-338.664249 333.472733l0 19.794109c0 96.139636 149.47278 96.139636 338.664249 96.139636l200.974278 0c181.747565 0 338.694591 0 338.694591-96.139636l0-19.794109C951.164031 720.158252 799.235571 570.546911 612.471463 570.546911z">
                                    </path>
                                </svg>
                            </el-avatar>
                        </div>
                    </template>
                    <template #dropdown>
                        <el-dropdown-menu class="user-avatar-menu">
                            <div v-if="user" style="inset: 0;text-align: center;font-size: 16px;margin: 5px">
                                {{ user.name }}
                            </div>
                            <el-dropdown-item v-if="user" command="logout">
                                <span>登出</span>
                            </el-dropdown-item>
                            <el-dropdown-item v-else command="login">
                                <span>登录</span>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </template>

                </el-dropdown>
                <div class="theme-btn">
                    <el-switch
                            size="large"
                            :inactive-action-icon="Sunny"
                            :active-action-icon="MoonNight"
                            :inline-prompt="true"
                            active-color="#111"
                            inactive-color="#eee"
                            v-model="dark"/>
                </div>
            </div>

        </div>
    </div>
</template>

<style lang="scss" scoped>
@use 'style/CommonHeader';
</style>

<script setup lang="ts">

import {ref, watch} from "vue";
import {getTheme, setTheme} from "Global";
import {MoonNight, Sunny} from "@element-plus/icons-vue";
import {Props} from "./CommonHeader.ts";

const props = defineProps<Props>()

const emits = defineEmits(["onUserLogin", "logout"])

function onCommand(c: string | number) {
    switch (c) {
        case 'logout': {
            emits('logout')
            break
        }
        case 'login': {
            if (!props.user) {
                emits("onUserLogin")
            }
            break
        }
    }
}


const dark = ref<boolean>(getTheme())

watch(dark, (nv) => {
    setTheme(nv)
    if (nv) {
        document.documentElement.classList.add("dark")
    } else {
        document.documentElement.classList.remove("dark")
    }
})

</script>
