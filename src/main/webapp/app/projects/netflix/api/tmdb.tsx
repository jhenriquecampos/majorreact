const API_KEY = 'api_key';
const API_BASE = 'https://api.themoviedb.org/3';

const basicFetch = async (endpoint: string) => {
  const req = await fetch(`${API_BASE}${endpoint}`);
  const json = req.json();
  return json;
};

export default {
  getHomeList: async () => {
    return [
      {
        slug: 'originals',
        title: 'Netflix Originals',
        items: await basicFetch(`/discover/tv?with_network=213&api_key=${API_KEY}`),
      },
      {
        slug: 'trending',
        title: 'Trending',
        items: await basicFetch(`/trending/all/week?api_key=${API_KEY}`),
      },
      {
        slug: 'toprated',
        title: 'Top Rated',
        items: await basicFetch(`/movie/top_rated?api_key=${API_KEY}`),
      },
    ];
  },
};
