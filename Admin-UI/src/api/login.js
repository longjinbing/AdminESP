import fetch from '@/utils/fetch';

function loginByUsername(username, password) {
  const data = { username, password };
  return fetch({
    // url: '/login/login',
    url: '/jwt/token',
    method: 'POST',
    data
  })
}

function logout() {
  return fetch({
    // url: '/login/logout',
    url: '/jwt/invalid',
    method: 'POST'
  })
}

function getUserInfo(token) {
  return fetch({
    // url: '/user/info',
    url: '/jwt/user',
    method: 'GET',
    params: { token }
  })
}

export { loginByUsername, logout, getUserInfo };

