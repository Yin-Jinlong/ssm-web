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
                                    :src="user.uid?'/img/avatar.svg':''">
                                <svg class="def-avatar" viewBox="0 0 1024 1024" width="200" height="200">
                                    <path d="M501.937582 545.097053c147.891962 0 268.231366-118.534055 268.223274-264.288929 0-145.746783-120.330301-264.28994-268.222263-264.28994-147.890951 0-268.221252 118.584625-268.221252 264.28994C233.71633 426.520519 354.046631 545.097053 501.937582 545.097053zM612.471463 570.546911 411.497184 570.546911c-186.760063 0-338.664249 149.569875-338.664249 333.472733l0 19.794109c0 96.139636 149.47278 96.139636 338.664249 96.139636l200.974278 0c181.747565 0 338.694591 0 338.694591-96.139636l0-19.794109C951.164031 720.158252 799.235571 570.546911 612.471463 570.546911z">
                                    </path>
                                </svg>
                            </el-avatar>
                        </div>
                    </template>
                    <template #dropdown>
                        <el-dropdown-menu class="user-avatar-menu">
                            <div v-if="user.uid" style="inset: 0;text-align: center;font-size: 16px;margin: 5px">
                                {{ user.name }}
                            </div>
                            <el-dropdown-item v-if="user.uid" command="logout">
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
@use 'sass:color';
@use '../../../yjl-var' as var;

$header-height : 100px;

.header {
  --header-height       : #{$header-height};
  --header-shadow-color : rgba(0, 0, 0, 0.2);
  --ds                  : 4px;

  position              : sticky;
  top                   : 0;
  left                  : 0;

  z-index               : 100;

  height                : var(--header-height);
  width                 : 100%;

  box-shadow            : var(--header-shadow-color) 0 0 3px;

}

html.dark {
  .header {
    --header-shadow-color : rgba(255, 255, 255, 0.2);
  }
}

.header-wrapper {
  display     : flex;
  align-items : center;
  min-width   : var(--min-width);
  max-width   : var(--max-width);
  height      : 100%;
  margin      : auto;
  position    : relative;

  > .logo {
    -webkit-user-drag   : none;
    -webkit-user-select : none;
    -moz-user-select    : none;
    user-select         : none;
    width               : max-content;
    height              : var(--header-height);
    display             : flex;
    align-items         : center;

    > .logo-img {
      width            : var(--header-height);
      height           : var(--header-height);
      background-image : url('/img/logo.png');
      background-size  : var(--header-height) var(--header-height);
    }

    .logo-text {
      user-select : none;
      color       : #{var.$color-aynu};
    }

    .logo-text-zh {
      font-family : 华文隶书, 隶书, serif;
      font-size   : 2.5em;
    }

    .logo-text-en {
      font-family : unset;
      font-size   : 0.98em;
    }

  }


}

.right-controller {
  display        : flex;
  flex-direction : row-reverse;
  position       : absolute;
  right          : 0;
  height         : 100%;
  align-items    : center;
}

.theme-btn {
  margin : 5px;
}

:deep(.el-switch__core .el-switch__action) {
  background-color : rgb(128, 128, 128, 0.2);
  color            : black;
}

:deep(.is-checked) .el-switch__core .el-switch__action {
  background-color : rgb(128, 128, 128, 0.2);
  color            : white;
}

.def-avatar {
  width            : 30px;
  height           : 30px;
  fill             : var(--bgc);
  background-color : transparent;
}

</style>

<script setup lang="ts">

import {ref, watch} from "vue";
import {getTheme, setTheme} from "Global";
import {MoonNight, Sunny} from "@element-plus/icons-vue";

const props = defineProps<{
    user: {
        uid?: number
        name: string
    }
}>()

const emits = defineEmits(["onUserLogin", "logout"])

function onCommand(c: string | number) {
    switch (c) {
        case 'logout': {
            emits('logout')
            break
        }
        case 'login': {
            if (!props.user.uid) {
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
