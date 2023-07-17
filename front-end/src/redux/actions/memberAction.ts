import { Member } from '../reducers/memberReducer';
import { register, login } from '../../librarys/api/members';

export function memberRegister(payload: Member) {
    register(payload)
    // return 값 재확인 필요
    return 1;
}

export function memeberLogin(payload: Member) {
    login(payload)
}