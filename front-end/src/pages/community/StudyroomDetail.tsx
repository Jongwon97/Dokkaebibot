import React from 'react';
import CommunityHeader from './comp/CommunityHeader';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';

function StudyroomDetail() {
  const studyroom = useSelector((state:RootState) => state.persistedReducer.studyroomReducer);
  return (
    <div className="Home">
      <CommunityHeader title={studyroom.title} tab='studyroom' />
    </div>
  );
}

export default StudyroomDetail;