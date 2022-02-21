import React, { useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import TMDB from './api/tmdb';
import MovieRow from './components/movie-row';
import './netflix.scss';

export const Netflix = (props: RouteComponentProps<any>) => {
  const [moviesList, setMoviesList] = useState([]);

  useEffect(() => {
    const loadAll = async () => {
      // get full movies list
      let list = await TMDB.getHomeList();
      console.log(list);
      setMoviesList(list);
    };

    loadAll();
  }, []);

  return (
    <div className="page">
      <section className="lists">
        {moviesList.map((item, key) => (
          <MovieRow key={key} title={item.title} items={item.items} />
        ))}
      </section>
    </div>
  );
};

export default Netflix;
