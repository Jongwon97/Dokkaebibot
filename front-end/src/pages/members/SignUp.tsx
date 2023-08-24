import React from "react";
import { Link, useNavigate } from 'react-router-dom';
import { useForm, SubmitHandler } from "react-hook-form";
import { InputText } from "primereact/inputtext";
import { Button } from "primereact/button";
import styles from "../../styles/pages/members/SignUp.module.scss"
import { Member } from "../../redux/reducers/memberReducer";
import { postMember } from "../../client/members";
import classNames from "classnames/bind";

const SignUp = () => {
    const cx = classNames.bind(styles)
    const { register, handleSubmit, getValues, formState } = useForm<Member>()
    const navigate = useNavigate();

    const onSubmitHandler: SubmitHandler<Member> = (data) => {
        postMember(data).then((response) => {
            if(response.data=== 'success'){
                alert("회원가입 성공!");
                navigate('/members/login', {replace:true})
            }
            else{
                alert("이미 사용되고 있는 이메일입니다.");
            }
        })
        .catch((e)=>console.log(e))
    }
    const emailRegex = new RegExp(/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/)
    const passwordRegex = new RegExp(/(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{6,})/)
    return (
        <div className={styles.signup}>
            <div className={styles.box}>
                <h2 className={styles.title}>회원가입</h2>
                <form onSubmit={handleSubmit(onSubmitHandler)}>
                    <p className={styles.left}>이메일로 회원가입</p>
                    <InputText {...register("email", {
                            pattern: {
                                value: emailRegex,
                                message: '이메일 형식이 올바르지 않습니다.'
                            }
                        }
                        )} placeholder="이메일" id="email" 
                        className={cx(styles.inputBox, formState.errors.email && styles.error)} 
                        autoFocus
                    />
                    <div className={styles.errorText}>{formState.errors.email?.message}</div>

                    <InputText {...register("nickname")} placeholder="닉네임" id="nickname" className={styles.inputBox} />
                    <InputText {...register("password", {
                        pattern: {
                            value: passwordRegex,
                            message: '비밀번호는 숫자, 소문자, 대문자를 포함한 6자 이상의 문자열이어야합니다.'
                        }
                    }
                    )} placeholder="비밀번호" id="password" type="password" 
                    className={cx(styles.inputBox, formState.errors.password && styles.error)} />
                    <div className={styles.errorText}>{formState.errors.password?.message}</div>

                    <InputText {...register("passwordConfirm", { required: true, 
                        validate: {
                            check: (val) => { 
                            if (getValues("password") !== val) {
                                return "비밀번호가 일치하지 않습니다."
                            }},
                        } })}
                    placeholder="비밀번호 확인" id="passwordConfirm" type="password" className={styles.inputBox} />
                    <div className={styles.errorText}>{formState.errors.passwordConfirm?.message}</div>
                    
                    <Button label="회원가입 하기" type="submit" className={styles.inputButton} />
                    <p className={styles.login}>
                        이미 우리 스터디원이신가요? 
                        <Link to={"/members/login"} className={styles.link}>로그인</Link>하기
                    </p>
                </form>
            </div>
            <p className={styles.privacy}>
                회원가입을 통해 계정을 생성하는 경우, 귀하는 도깨비봇의 서비스 약관에 동의하게 됩니다.
                <br />
                도깨비봇의 개인정보 처리에 대한 자세한 내용은 개인정보처리방침을 참조하시기 바랍니다.
            </p>
        </div>
    )
}

export default SignUp;