import React, { useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import TMDB from './api/tmdb';

export const Netflix = (props: RouteComponentProps<any>) => {
  useEffect(() => {
    const loadAll = async () => {
      // get full movies list
      let list = await TMDB.getHomeList();
      console.log(list);
    };

    loadAll();
  }, []);

  return <div>Netflix Home Page!</div>;
};

export default Netflix;
