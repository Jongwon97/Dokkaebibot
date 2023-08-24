import { client, clientWithToken } from "./client";
import state01 from '../../src/assets/dokkaebi.png';
import state02 from '../../src/assets/dokkaebi_phone.png';
import state03 from '../../src/assets/dokkaebi_sleep.png';
import state04 from '../../src/assets/dokkaebi_away.png';
import state05 from '../../src/assets/dokkaebi_study.png';
import state06 from '../../src/assets/dokkaebi_bad.png';
import ssafy01 from '../../src/assets/ssafy.png';
import ssafy02 from '../../src/assets/ssafy_phone.png';
import ssafy03 from '../../src/assets/ssafy_sleep.png';
import ssafy04 from '../../src/assets/ssafy_away.png';
import ssafy05 from '../../src/assets/ssafy_study.png';
import ssafy06 from '../../src/assets/ssafy_bad.png';

const membersURL = 'members/'

export const postMember = ({ email, nickname, password }:
    {email: string, nickname: string, password: string}) => {
    return client().post(membersURL + 'register', { email, nickname, password });
};

export const memberLogin = ({ email, password }:
    {email: string, password: string}) => {
    return client().post(membersURL + 'login', { email, password });
};

export const memberLogout = () => {
    return clientWithToken().get(membersURL + 'logout/check');
};

export const getMemberInfo = () => {
    return clientWithToken().get(membersURL + 'info/check')
}

export const findMemberByEmail = (email:string, id:number) => {
    // return client().get(membersURL + 'search?email=' + email + "?id=" + id)
    return client().get(membersURL + 'search', {
        params: {
            email: email,
            id: id
        }
    })
}

export const getIcon = (iconNum:number) => {
    const icons = [state01, state02, state03, state04, state05, state06, ssafy01, ssafy02, ssafy03, ssafy04, ssafy05, ssafy06]
    return icons[iconNum]
}

// 관심사 불러오기
export const getInterests=()=>{
    return clientWithToken().get(membersURL + 'find/interests/check')
}

// 관심사 저장
export const registInterests = (interests:any) => {
    return clientWithToken().post(membersURL + 'regist/interests/check', interests);
};

// 한줄소개 저장
export const registIntroduction = (introduction:any) => {
    return clientWithToken().post(membersURL + 'regist/introduction/check', introduction);
};

// 한줄소개 불러오기
export const getIntroduction = () => {
    return clientWithToken().get(membersURL + 'find/introduction/check')
}

// 닉네임 바꾸기
export const updateNickname = (changeTo: string) => {
    return clientWithToken().put(membersURL + 'nickname/check', changeTo)
}

// 비밀번호 바꾸기
export const updatePassword = (data: object) => {
    return clientWithToken().put(membersURL + 'password/check', data)
}