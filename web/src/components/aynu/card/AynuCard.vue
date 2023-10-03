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
            <el-card style="padding: 3em 1em 1em 1em;position: relative;width: calc(100% - 2em)">
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
@use '../../../yjl-fun' as f;
@use 'element-plus/theme-chalk/src/card';

.card-content {
  display : flex;
  color   : f.blackAlpha(0.8);
}

.base-info {
  width        : max-content;
  border-right : #{f.blackAlpha(0.2)} solid 1px;
}

.time {
  position : absolute;
  top      : 0.9em;
  left     : 140px;
  color    : rgba(128, 128, 128, 0.5);
}

html.dark {
  .base-info {
    border-right : #{f.whiteAlpha(0.2)} solid 1px;
  }

  .card-content {
    color : f.whiteAlpha(0.8);
  }
}

.avatar {
  width         : 60px;
  border-radius : 100%;
  margin        : 0 2em 0 1em;
}

.name {
  align-items        : center;
  margin-top         : 1em;
  width              : calc(60px + 2em);
  height             : 2em;
  text-align         : center;
  line-height        : 1em;
  // 超出部分省略号
  word-break         : break-all;
  overflow           : hidden;
  display            : -webkit-box;
  -webkit-line-clamp : 2;
  -webkit-box-orient : vertical;
}

.card-btn-container {
  position : absolute;
  display  : flex;
  top      : 5px;
  right    : 0.5em;
}

.card-btn {
  margin-left : 1em;
}

.msg {
  margin-left   : 2em;
  width         : 100%;
  padding       : 3px;
  border        : rgba(211, 231, 179, 0.45) solid 1px;
  border-radius : 10px;
}

.skeleton-box {
  display          : flex;
  height           : 178px;
  align-items      : center;
  background-color : #fafafa;
  border-radius    : 10px
}

html.dark .skeleton-box {
  background-color : #151515
}

</style>

<script setup lang="ts">

import {TopTooltip} from "@components";

const props = defineProps<{
    data?: {
        name: string
        img: string
        msg: string
        time: Date
    } | null,
    onDelete: () => void
}>()

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
