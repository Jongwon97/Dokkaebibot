import React from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/store';

function StudyroomMemberItem() {
  const user = useSelector((state:RootState) => state.persistedReducer.memberReducer);
  return (
    <div>
      
    </div>
  );
}

export default StudyroomMemberItem;