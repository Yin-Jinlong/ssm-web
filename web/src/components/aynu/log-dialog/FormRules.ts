import {InternalRuleItem, SyncValidateResult, ValidateOption, Values} from "async-validator";
import {Ref} from "vue";
import {FormItemRule, FormRules} from "element-plus";
import {reactive} from "vue";

export interface FormData {
    logid: string,
    pwd: string
    pwd1: string
    pwd2: string
}


export interface LogUser {
    logid: string
    name: string
    pwd: string
    pwd1: string
    pwd2: string
}

let logUser: Ref<LogUser>

export function initFromRules(u: Ref<LogUser>) {
    logUser = u
}

function checkLogid(
    rule: InternalRuleItem,
    value: string,
    callback: (error?: string | Error) => void,
    source: Values,
    options: ValidateOption): SyncValidateResult | void {
    if (value.match(/^\d{6}$/))
        return callback()
    if (value.match(/^\d+$/))
        return callback("id应为6位数字")
    if (value.match(/^.{1,12}$/))
        return callback()
    return callback("用户名长度1-12")
}

function checkUname(
    rule: InternalRuleItem,
    value: string,
    callback: (error?: string | Error) => void,
    source: Values,
    options: ValidateOption): SyncValidateResult | void {
    if (value.match(/^\d+$/))
        return callback("用户名不能全为数字")
    if (value.match(/^.{1,12}$/))
        return callback()
    return callback("用户名长度1-12")
}

function checkPwd2(
    rule: InternalRuleItem,
    value: string,
    callback: (error?: string | Error) => void,
    source: Values,
    options: ValidateOption): SyncValidateResult | void {
    if (value != logUser.value.pwd1)
        return callback("两次密码不一致")
    return callback()
}


const pwdRule = [
    {required: true, message: '请输入密码', trigger: 'change'},
    {min: 6, max: 18, message: '密码长度6-18', trigger: 'change'}
] as FormItemRule[]

export const loginRules = reactive<FormRules<FormData>>({
    logid: [
        {required: true, message: '请输用户名或id', trigger: 'change'},
        {validator: checkLogid, trigger: 'change'},
    ],
    pwd: pwdRule
})


export const logonRules = reactive<FormRules<FormData>>({
    logid: [
        {required: true, message: '请输用户名', trigger: 'change'},
        {validator: checkUname, trigger: 'change'},
    ],
    pwd1: pwdRule,
    pwd2: [
        {required: true, message: '请输入密码', trigger: 'change'},
        {validator: checkPwd2, trigger: 'change'}
    ]
})
