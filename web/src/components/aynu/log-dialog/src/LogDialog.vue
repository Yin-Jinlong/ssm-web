<template>
    <el-dialog
            :model-value="modelValue"
            @open="emits('update:modelValue',true)"
            @close="emits('update:modelValue',false)"
            :show-close="false"
            class="noheader"
            width="400">
        <el-tabs
                :model-value="nowPage"
                @tabChange="emits('update:nowPage', $event)"
                :stretch="true">
            <el-tab-pane
                    name="login"
                    label="登录">
                <el-form ref="form"
                         :model="logUser"
                         :rules="loginRules"
                         label-width="80">
                    <el-form-item label="账号"
                                  prop="logid"
                                  required>
                        <el-input
                                v-model="logUser.logid"
                                :formatter="(v:string)=>{return v.replace(/\s+/,'')}"
                                maxlength="12"
                                placeholder="用户名或id"
                                type="text"/>
                    </el-form-item>
                    <el-form-item label="密码"
                                  prop="pwd"
                                  required>
                        <el-input v-model="logUser.pwd"
                                  :show-password="true"
                                  maxlength="18"
                                  placeholder="密码"
                                  type="password"/>
                    </el-form-item>
                </el-form>
                <div class="options">
                    <el-checkbox
                            v-model="autoLogin">
                        自动登录
                    </el-checkbox>
                </div>
                <el-button :loading="isLoging"
                           style="width: 100%;height: 4em"
                           type="primary"
                           @click.stop="login"><span>{{ isLoging ? '登陆中...' : '登录' }}</span></el-button>
            </el-tab-pane>
            <el-tab-pane
                    name="logon"
                    label="注册">
                <el-form :model="logUser"
                         :rules="logonRules"
                         ref="logonForm"
                         label-width="80">
                    <el-form-item label="用户名"
                                  prop="logid"
                                  required>
                        <el-input v-model="logUser.logid"
                                  maxlength="12"
                                  placeholder="用户名"/>
                    </el-form-item>
                    <el-form-item label="密码"
                                  prop="pwd1"
                                  required>
                        <el-input v-model="logUser.pwd1"
                                  :show-password="true"
                                  maxlength="18"
                                  placeholder="密码"
                                  type="password"/>
                    </el-form-item>
                    <el-form-item label="确认密码"
                                  prop="pwd2"
                                  required>
                        <el-input v-model="logUser.pwd2"
                                  maxlength="18"
                                  placeholder="确认"
                                  type="password"/>
                    </el-form-item>
                </el-form>
                <el-button :loading="isLoging"
                           style="width: 100%;height: 4em"
                           type="primary"
                           @click.stop="logon"><span>{{ isLoging ? '请稍后...' : '注册' }}</span></el-button>
            </el-tab-pane>
        </el-tabs>
    </el-dialog>
</template>

<style lang="scss" scoped>
@use 'style/LogDialog';
</style>

<style>
.noheader .el-dialog__header {
    padding: 0;
}
</style>

<script lang="ts" setup>
import {ElForm} from "element-plus";
import {reactive, ref, watch} from "vue";
import {initFromRules, loginRulesDefine, logonRulesDefine, LogUser} from "./FormRules.ts";
import {useStatuser} from "@util/Statuser.ts";

const props = defineProps<{
    modelValue?: boolean
    nowPage: 'login' | 'logon'
}>()

const emits = defineEmits(["login", "logon", "update:nowPage", 'update:modelValue'])

const isLoging = ref(false)

const statuser = useStatuser("logdialog")

const autoLogin = statuser.useRef<boolean>('autoLogin', false)

const form = ref<InstanceType<typeof ElForm>>()
const logonForm = ref<InstanceType<typeof ElForm>>()

const loginRules = reactive(loginRulesDefine)
const logonRules = reactive(logonRulesDefine)

let logUser = ref<LogUser>({
    logid: '',
    name: '',
    pwd: '',
    pwd1: '',
    pwd2: ''
})

initFromRules(logUser)

function clearPwds() {
    logUser.value.pwd = ''
    logUser.value.pwd1 = ''
    logUser.value.pwd2 = ''
}

function logEnd(ok: boolean) {
    if (ok) {
        // 清除密码，避免泄露
        clearPwds()
    }
    setTimeout(() => {
        isLoging.value = false
    }, 500)
}

function login() {
    form.value!.validate((valid: boolean) => {
        if (valid) {
            isLoging.value = true
            emits("login", logUser.value, logEnd)
        }
    })
}

function logon() {
    logonForm.value?.validate(valid => {
        if (valid) {
            isLoging.value = true
            emits("logon", logUser.value, logEnd)
        }
    })
}

watch(props, (nv) => {
    if (!form.value || !logonForm.value)
        return
    if (nv.modelValue) {
        form.value!.clearValidate()
        logonForm.value!.clearValidate()
    }
}, {
    deep: true
})
</script>
