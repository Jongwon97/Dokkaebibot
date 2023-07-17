import { setMemberData } from '../reducers/memberReducer';
import { Member } from '../reducers/memberReducer';
import { register } from '../../librarys/api/members';
import { Dispatch } from 'redux';

export function memberRegister(payload: Member) {
    register(payload)
    // return 값 재확인 필요
    return 1;
}