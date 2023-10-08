import {FormRules} from "element-plus";

export declare interface Props {
    modalValue?: boolean,
}

export interface FormData {
    msg: string,
}

export const formRulesDefine = {
    msg: [
        {required: true, message: '请输入留言', trigger: 'change'}
    ],
} as FormRules<FormData>


