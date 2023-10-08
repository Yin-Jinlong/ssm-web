<template>
    <el-dialog
            v-if="user"
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
                            :src="user.img"/>
                    <div style="padding: 1em">
                        <h3 style="">{{ user.name }}</h3>
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
                        @click.stop="onsubmit"
                        type="primary"><span>完成</span></el-button>
            </top-tooltip>
        </template>
    </el-dialog>
</template>

<style scoped lang="scss">
@use 'style/add-msg-dialog';
</style>

<script setup lang="ts">

import {reactive, ref, watch} from "vue";
import {ElForm} from "element-plus";
import {TopTooltip} from "@components";
import {Props, formRulesDefine, FormData} from "./add-msg-dialog.ts";
import {globalStatuser} from "@util/Statuser.ts";
import {User} from "@types";

const props = defineProps<Props>()

const user=globalStatuser.useRef<User|null>('user',null)

const emits = defineEmits(['onAdd'])

const form = ref<InstanceType<typeof ElForm>>()

const disableSubmit = ref(true)

const data = reactive<FormData>({
    msg: "",
})

const formRules = reactive(formRulesDefine)

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
            data.msg = ''
        } else {
            return false
        }
    })
}

</script>