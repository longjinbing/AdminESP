import fetch from 'utils/fetch';

export function page(query) {
  return fetch({
    url: '/api/admin/user/page',
    method: 'get',
    params: query
  })
}

export function addObj(obj) {
  return fetch({
    url: '/api/admin/user/add',
    method: 'post',
    data: obj
  })
}

export function getObj(id) {
  return fetch({
    url: `/api/admin/user/get/${id}`,
    method: 'get'
  })
}

export function delObj(id) {
  return fetch({
    url: `/api/admin/user/delete/${id}`,
    method: 'delete'
  })
}

export function putObj(obj) {
  return fetch({
    url: '/api/admin/user/put/selective',
    method: 'put',
    data: obj
  })
}

export function getAll() {
  return fetch({
    url: '/api/admin/user/getAll',
    method: 'get'
  })
}

export function addObjhome(obj) {
  return fetch({
    url: '/api/admin/userHome/add',
    method: 'post',
    data: obj
  })
}

export function getObjhome(id) {
  return fetch({
    url: '/api/admin/userHome/get/' + id,
    method: 'get'
  })
}

export function delObjhome(id) {
  return fetch({
    url: '/api/admin/userHome/delete/' + id,
    method: 'delete'
  })
}

export function getObjhomeAll() {
  return fetch({
    url: '/api/admin/userHome/all',
    method: 'get'
  })
}

export function putObjHome(id, obj) {
  return fetch({
    url: '/api/admin/userHome/put/' + id,
    method: 'put',
    data: obj
  })
}

export function getObjHomeByQuery(query) {
  return fetch({
    url: '/api/admin/userHome/list',
    method: 'get',
    params: query
  })
}

export function getObjhomeByUserId(id) {
  return fetch({
    url: '/api/admin/userHome/username/' + id,
    method: 'get'
  })
}

export function addObjGraduate(obj) {
  return fetch({
    url: '/api/admin/userGraduate/add',
    method: 'post',
    data: obj
  })
}

export function getObjGraduate(id) {
  return fetch({
    url: '/api/admin/userGraduate/get/' + id,
    method: 'get'
  })
}

export function delObjGraduate(id) {
  return fetch({
    url: '/api/admin/userGraduate/delete/' + id,
    method: 'delete'
  })
}

export function putObjGraduate(id, obj) {
  return fetch({
    url: '/api/admin/userGraduate/put/' + id,
    method: 'put',
    data: obj
  })
}

export function getObjGraduateByQuery(query) {
  return fetch({
    url: '/api/admin/userGraduate/list',
    method: 'get',
    params: query
  })
}

export function addObjContract(obj) {
  return fetch({
    url: '/api/admin/userContract/add',
    method: 'post',
    data: obj
  })
}

export function getObjContract(id) {
  return fetch({
    url: '/api/admin/userContract/get/' + id,
    method: 'get'
  })
}

export function delObjContract(id) {
  return fetch({
    url: '/api/admin/userContract/delete/' + id,
    method: 'delete'
  })
}

export function putObjContract(id, obj) {
  return fetch({
    url: '/api/admin/userContract/put/' + id,
    method: 'put',
    data: obj
  })
}

export function getObjContractByQuery(query) {
  return fetch({
    url: '/api/admin/userContract/list',
    method: 'get',
    params: query
  })
}
