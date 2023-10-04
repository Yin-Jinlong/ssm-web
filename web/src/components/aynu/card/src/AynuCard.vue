<template>
    <el-skeleton
            animated
            :loading="!data">
        <template #template>
            <div class="skeleton-box">
                <div style="display:flex;flex-direction: column;align-items: center;border-right: solid 1px gray">
                    <el-skeleton-item variant="circle" style="height: 60px;width: 60px;margin: 5px 30px;"/>
                    <el-skeleton-item variant="text" style="width: 60px;"/>
                </div>
                <div style="width: 80%;margin-left:  40px">
                    <el-skeleton-item variant="text" style="width: 33%;height: 20px"/>
                    <el-skeleton-item variant="text" style="height: 25px"/>
                    <el-skeleton-item variant="text" style="height: 25px"/>
                </div>
            </div>
        </template>
        <template #default>
            <el-card class="card">
                <div class="card-content">
                    <div class="base-info">
                        <el-image
                                class="avatar"
                                :src="props?.data?.img"/>
                        <div class="name">{{ props?.data?.name }}</div>
                    </div>
                    <div class="msg">
                        {{ props?.data?.msg }}
                    </div>
                    <div class="time">
                        {{ toDateStr(props?.data?.time) }}
                    </div>
                </div>
                <div class="card-btn-container">
                    <div class="card-btn">
                        <top-tooltip
                                content="编辑">
                            <el-button><span>编辑</span></el-button>
                        </top-tooltip>
                    </div>
                    <top-tooltip
                            content="删除">
                        <div class="card-btn">
                            <el-popconfirm
                                    :width="100"
                                    :confirm-button-type="'danger'"
                                    @confirm="onDelete"
                                    title="确认删除?">
                                <template #reference>
                                    <el-button
                                            type="danger"><span>删除</span></el-button>
                                </template>
                            </el-popconfirm>
                        </div>
                    </top-tooltip>
                </div>
            </el-card>
        </template>
    </el-skeleton>
</template>

<style scoped lang="scss">
@use 'style/AynuCard';
</style>

<script setup lang="ts">

import {TopTooltip} from "@components";
import {Props} from "./AynuCard.ts";

const props = defineProps<Props>()

function toDateStr(time: Date | undefined): string {
    if (!time)
        return ''
    let y = time.getFullYear()
    let M = time.getMonth()
    let d = time.getDate()
    let h = time.getHours()
    let m = time.getMinutes()
    return `${y} ${M}-${d} ${h}:${m}`
}

</script>
