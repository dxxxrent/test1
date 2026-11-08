import request from '@/utils/request'

export function fetchNoticeList(query) {
  return request({ url: '/notice/list', method: 'get', params: query })
}

export function createNotice(notice) {
  return request({ url: '/notice/create', method: 'post', params: notice })
}

export function updateNotice(notice) {
  return request({ url: '/notice/update', method: 'post', params: notice })
}

export function deleteNotice(id) {
  return request({ url: '/notice/delete', method: 'post', params: { id } })
}
