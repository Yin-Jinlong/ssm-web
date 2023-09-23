<template>
    <el-dialog
           :model-value="props.modalValue"
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
</template>

<style scoped lang="scss">

</style>

<script setup lang="ts">
import {ElForm, ElMessage, FormRules} from "element-plus";
import {reactive, ref} from "vue";
import axios from "axios";

const props = defineProps<{
    modalValue?: boolean
}>()

const emits = defineEmits(["login"])

const isLogining = ref(false)

const form=ref<InstanceType<typeof ElForm>>()

let logUser = ref<{
    uid: string
    pwd: string
}>({
    uid: '',
    pwd: ''
})

interface FormData {
    uid: string,
    pwd: string
}

const formRules = reactive<FormRules<FormData>>({
    uid: [
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
                    emits("login", res.data.user)
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

</script>
