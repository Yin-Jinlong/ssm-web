<template>
    <el-dialog
            :model-value="props.modalValue">
        <template #header>
            <h3 style="display:block;width: 100%;text-align: center"><span>添加留言</span></h3>
        </template>
        <template #default>
            <el-form
                    ref="form"
                    :label-position="'top'"
                    :model="data"
                    :rules="formRules"
                    label-width="120px">
                <div style="display:flex;align-items: center">
                    <el-image
                            class="avatar"
                            :src="data.img"/>
                    <div style="padding: 1em">
                        <h3 style="">{{user.name}}</h3>
                    </div>
                </div>
                <el-form-item
                        prop="msg"
                        :required="true"
                        style="width: 100%;"
                        label="留言">
                    <el-input
                            type="textarea"
                            :autosize="{minRows:5,maxRows:7}"
                            :rows="5"
                            v-model="data.msg"/>
                </el-form-item>
            </el-form>
        </template>
        <template #footer>
            <top-tooltip
                    :content="disableSubmit?'请先完成表单！':'提交'">
                <el-button
                        :disabled="disableSubmit"
                        @click.stop="onsubmit()"
                        type="primary"><span>完成</span></el-button>
            </top-tooltip>
        </template>
    </el-dialog>
</template>

<style scoped lang="scss">
.avatar {
  width         : 60px;
  height        : 60px;
  border-radius : 30px;
}
</style>

<script setup lang="ts">

import {reactive, ref, watch} from "vue";
import {ElForm, FormRules} from "element-plus";
import {TopTooltip} from "@components";

const props = defineProps<{
    modalValue?: boolean,
    user: {
        uid?: number
        name: string
    }
}>()

const emits = defineEmits(['onAdd'])

const form = ref<InstanceType<typeof ElForm>>()

const disableSubmit = ref(true)

interface FormData {
    img?: string,
    name: string,
    msg: string,
}

const data = reactive<FormData>({
    msg: "",
    name: "",
    img: '/img/avatar.svg'
})

const formRules = reactive<FormRules<FormData>>({
    name: [
        {required: true, message: '请输入用户名', trigger: 'change'},
        {min: 3, max: 12, message: '长度在 3 到 12 个字符', trigger: 'change'}
    ],
    msg: [
        {required: true, message: '请输入留言', trigger: 'change'}
    ],
    img: [
        {required: false, message: '请上传头像', trigger: 'blur'}
    ]
})

watch(data, () => {
    form.value!.validate((valid: boolean) => {
        disableSubmit.value = !valid
    })
}, {
    deep: true
})

function onsubmit() {
    form.value!.validate((valid: boolean) => {
        if (valid) {
            emits('onAdd', data.msg)
            data.img = '/img/avatar.svg'
            data.msg = ''
        } else {
            return false
        }
    })
}

</script>