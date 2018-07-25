import Mock from 'mockjs'
import { param2Obj } from '@/utils'

const NameList = []
const count = 100

for (let i = 0; i < count; i++) {
  NameList.push(Mock.mock({
    name: '@first'
  }))
}
NameList.push({ name: 'mockPan' })

export default {
  searchUser: config => {
    const { name } = param2Obj(config.url)
    const mockNameList = NameList.filter(item => {
      const lowerCaseName = item.name.toLowerCase()
      if (name && lowerCaseName.indexOf(name.toLowerCase()) < 0) return false
      return true
    })
    return { items: mockNameList }
  },
  userList: config => {
    const list = [];
    for (let i = 0; i < 20; i++) {
      const email = Mock.Random.email('petrochina.com');
      list.push(Mock.mock({
        name: '@cname',
        username: email.substring(0, email.indexOf('@')),
        email,
        mobile: /^1[385][1-9]\d{8}/,
        enabled: 1
      }));
    }

    return { data: { data: { total: 3198, rows: list } } };
  }
}
