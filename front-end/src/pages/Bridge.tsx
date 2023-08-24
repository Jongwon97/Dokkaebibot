import React, { useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { getMemberInfo } from '../client/members';
import { useDispatch } from 'react-redux';
import { setMemberData } from '../redux/reducers/memberReducer';

function Bridge(this: any) {
	// intercept token in this page
  const [searchParams, setSearchParams] = useSearchParams();
  const accessToken: string|null = searchParams.get('accessToken');
  const refreshToken: string|null = searchParams.get('refreshToken');
  // save in local storage
  // NEED to change after setting https
  if (accessToken != null) {
    localStorage.setItem("accessToken", accessToken)
  }
  if (refreshToken != null) {
    localStorage.setItem("refreshToken", refreshToken)
  }
  const navigate = useNavigate();
  useEffect(()=>{
    navigate('/bridge/bridge', {replace:true});
  })
  return (
    <div className="Bridge">
      Bridge
    </div>
  );
}

export default Bridge;