import {FormRules} from "element-plus";
import {User} from "@types";

export declare interface Props {
    modalValue?: boolean,
    user: User | null
}

export interface FormData {
    msg: string,
}

export const formRulesDefine = {
    msg: [
        {required: true, message: '请输入留言', trigger: 'change'}
    ],
} as FormRules<FormData>


