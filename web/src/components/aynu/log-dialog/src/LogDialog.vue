<template>
    <el-dialog
            :model-value="props.modalValue"
            :show-close="false"
            class="noheader"
            width="400">
        <el-tabs :stretch="true">
            <el-tab-pane label="登录">
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

                <el-button :loading="isLogining"
                           style="width: 100%;height: 4em"
                           type="primary"
                           @click.stop="login"><span>{{ isLogining ? '登陆中...' : '登录' }}</span></el-button>
            </el-tab-pane>
            <el-tab-pane label="注册">
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
                <el-button :loading="isLogoning"
                           style="width: 100%;height: 4em"
                           type="primary"
                           @click.stop="logon"><span>{{ isLogoning ? '请稍后...' : '注册' }}</span></el-button>
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
import {ElForm, ElMessage} from "element-plus";
import {onMounted, reactive, ref, watch} from "vue";
import axios, {AxiosError} from "axios";
import {initFromRules, loginRulesDefine, logonRulesDefine, LogUser} from "./FormRules.ts";
import {User} from "@types";
import {getAutoLogin, LS, setAutoLogin} from "Global";

const props = defineProps<{
    modalValue?: boolean
}>()

const emits = defineEmits(["login"])

const isLogining = ref(false)
const isLogoning = ref(false)
const autoLogin = ref(getAutoLogin())

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

function catchError(err: any) {
    if (err instanceof AxiosError) {
        let rd = err?.response?.data
        if (rd) {
            ElMessage.error(rd.msg)
            return
        }
    }
    ElMessage.error(err)
}

async function postLogin(logid: string, pwd: string | undefined = undefined): Promise<User> {
    let args = 'logid=' + logid
    if (pwd)
        args += '&pwd=' + pwd
    return new Promise<User>((resolve: (u: User) => void, reject: (r: any) => void) => {
        axios.post("/api/user/login", args).then(res => {
            if (res.data.code == '0') {
                let user = res.data.user as User
                resolve(user)
            } else {
                reject(res.data.msg)
            }
        }).catch(err => {
            reject(err)
        })
    })
}

function clearPwds() {
    logUser.value.pwd = ''
    logUser.value.pwd1 = ''
    logUser.value.pwd2 = ''
}

function login() {
    form.value!.validate((valid: boolean) => {
        if (valid) {
            isLogining.value = true
            postLogin(logUser.value.logid, logUser.value.pwd).then(user => {
                // 清除密码，避免泄露
                clearPwds()
                ElMessage.success("登录成功")
                emits("login", user)
            }).catch(catchError).finally(() => {
                setTimeout(() => {
                    isLogining.value = false
                }, 500)
            })
        }
    })
}

function logon() {
    logonForm.value?.validate(v => {
        if (v) {
            isLogoning.value = true
            axios.post("/api/user/logon", `uname=${logUser.value.logid}&pwd=${logUser.value.pwd1}`).then(res => {
                if (res.data.code == '0') {
                    // 清除密码，避免泄露
                    clearPwds()
                    ElMessage.success(res.data.msg)
                    emits("login", res.data.user)
                } else {
                    ElMessage.error(res.data.msg)
                }
            }).catch(catchError).finally(() => {
                setTimeout(() => {
                    isLogoning.value = false
                }, 500)
            })
        }
    })
}

onMounted(() => {
    if (autoLogin.value) {
        let un = localStorage.getItem(LS.USER_NAME);
        if (un) {
            postLogin(un).then(user => {
                ElMessage.success(user.name + " 欢迎回来！")
                emits("login", user)
            }).catch(() => {
            })
        }
    }
})

watch(autoLogin, (nv) => {
    setAutoLogin(nv)
})

</script>
