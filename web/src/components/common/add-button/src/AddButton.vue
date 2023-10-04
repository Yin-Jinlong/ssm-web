<template>
    <div class="add-btn-frame">
        <div class="add-btn-container">
            <div class="add-btn-container-wrapper">
                <top-tooltip
                        content="添加">
                    <el-button
                            @click.stop="emits('click')"
                            class="add-btn"
                            type="primary">
                        <el-icon class="max-size">
                            <Plus/>
                        </el-icon>
                    </el-button>
                </top-tooltip>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">
@use 'style/AddButton';
</style>

<script setup lang="ts">
import {Plus} from "@element-plus/icons-vue";
import {TopTooltip} from "@components";

const emits = defineEmits(["click"])
</script>
