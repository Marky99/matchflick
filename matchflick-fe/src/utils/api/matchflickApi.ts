import axios from "axios";
import {ACCESS_TOKEN, REFRESH_TOKEN, USERNAME} from "../constants.ts";

const GATEWAY_URL = "http://localhost:8080";

const api = axios.create({
  baseURL: GATEWAY_URL
});

interface LoginRequest {
  username: string;
  password: string
}

interface PageRequest {
  page: number;
  size: number;
}

export type GenreResponse = {
  name: string
  score: number
  decisionCount: number
}

export type GenericNameDescResponse = {
  id: string
  name: number
  description: number
}

export type Review = {
  title: string
  score: number
  description: string
}

export type Match = {
  hostUsername: string
  baseEntityName: string
  friend: string
}

export type Movie = {
  category: Categories
  countryOfOrigin: string
  creator: string
  description: string
  duration: number
  id: string
  languages: string[]
  name: string
  publicationDate: Date
  rating: number
  trailerLink: string
}

enum Categories {
  MOVIES = "Movies"
}


export const login = async ({username, password}: LoginRequest) => {
  localStorage.clear();
  try {
    const response = await axios.post(GATEWAY_URL + "/auth/login", {
      username: username,
      password: password
    }, {
      headers: {
        'Content-Type': 'application/json'
      }
    });
    localStorage.setItem(ACCESS_TOKEN, response.data.accessToken);
    localStorage.setItem(REFRESH_TOKEN, response.data.refreshToken);
    localStorage.setItem(USERNAME, username)
  } catch (error) {
    console.error("Login failed:", error); // Handle login error
  }
}

export const getGenres = async ({page, size}: PageRequest) => {
  const {data} = await api.get(`/profiles/genres?page=${page}&size=${size}`)
  return data.content;
}

export const getAllGenres = async ({page, size}: PageRequest) => {
  const {data} = await api.get(`/profiles/genres/all?page=${page}&size=${size}`)
  return data.content;
}

export const assignGenre = async (ids: string[]) => {
  const {data} = await api.post(`/profiles/genres`, ids)
  return data;
}

export const getMyReviews = async ({page, size}: PageRequest) => {
  const {data} = await api.get(`/reviews?page=${page}&size=${size}`)
  return data.content;
}

export const getMyLastMatches = async () => {
  const {data} = await api.get(`/profiles/matches`)
  return data.content;
}

export const getMyTopFriends = async () => {
// TODO backend does not work
}

export const startSession = async (username: string) => {
  await api.post(`/profiles/session`, {username: username})
}

export const getCurrentActiveSession = async () => {
  const {data} = await api.get(`/profiles/session`)
  return data
}

export const endSession = async (username: string) => {
  await api.put(`/profiles/session`, {username: username})
}

export const getMovie = async () => {
  const {data} = await api.get(`/movies/session`)
  return data;
}

export const makeDecision = async (baseEntityId: string, decision: boolean) => {
  await api.post(`/movies/session`, {baseEntityId: baseEntityId, decision: decision})
}

export const isMatch = async () => {
  const {data} = await api.get(`/movies/session/match`)
  return data;
}

export const getLastReview = async () => {
  const {data} = await api.get(`/reviews/last`)
  return data;
}

export const postLastReview = async (id: string, {title, score, description}: Review) => {
  const {data} = await api.post(`/reviews/last?baseEntityId=${id}`, {
    title: title,
    score: score,
    description: description
  })
  return data;
}


api.defaults.headers.common["Content-Type"] = "application/json";
api.defaults.headers.common["Access-Control-Allow-Origin"] = "*";

api.interceptors.request.use(
    async (config) => {
      const accessToken = localStorage.getItem(ACCESS_TOKEN);
      if (accessToken) {
        config.headers['Authorization'] = `Bearer ${accessToken}`;
      }
      return config;
    },
    function (error) {
      // Handle error
      if (error.response.status === 401 || error.response.status === 403) {
        console.log("expired");
      }
      return Promise.reject(error);
    }
);

api.interceptors.response.use(
    function (response) {
      return response;
    },
    async function (error) {
      if (error.response.status === 401 || error.response.status === 403) {
        const refreshToken = sessionStorage.getItem("refresh_token");
        if (refreshToken) {
          const response = await api.post("/auth/refresh", {refreshToken: refreshToken})
          sessionStorage.setItem(ACCESS_TOKEN, response.data.accessToken);
          sessionStorage.setItem(REFRESH_TOKEN, response.data.refreshToken);
        }
      }
      return Promise.reject(error);
    }
);

