export const getAccessToken = (): string | null => {
  const data = sessionStorage.getItem(
      `accessToken`,
  );
  if (!data) return null;
  return JSON.parse(data).access_token || null;
};

export const getRefreshToken = (): string | null => {
  const data = sessionStorage.getItem(
      `refreshToken`,
  );
  if (!data) return null;
  return JSON.parse(data).refresh_token || null;
};