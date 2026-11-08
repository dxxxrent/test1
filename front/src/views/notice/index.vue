<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.title" placeholder="公告标题" clearable class="filter-item" style="width: 220px" @keyup.enter.native="handleSearch" />
      <el-select v-model="listQuery.status" placeholder="全部状态" clearable class="filter-item" style="width: 140px" @change="handleSearch">
        <el-option label="草稿" :value="0" />
        <el-option label="已发布" :value="1" />
      </el-select>
      <el-button v-waves type="primary" icon="el-icon-search" class="filter-item" @click="handleSearch">搜索</el-button>
      <el-button type="success" icon="el-icon-plus" class="filter-item" @click="openCreate">新增公告</el-button>
    </div>

    <el-table v-loading="loading" :data="list" border fit highlight-current-row>
      <el-table-column prop="title" label="公告标题" min-width="220" />
      <el-table-column label="内容摘要" min-width="300">
        <template slot-scope="scope">
          <span>{{ scope.row.content }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="110" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">{{ scope.row.status === 1 ? '已发布' : '草稿' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="170" align="center">
        <template slot-scope="scope">{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</template>
      </el-table-column>
      <el-table-column label="操作" width="190" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="openEdit(scope.row)">编辑</el-button>
          <el-button :type="scope.row.status === 1 ? 'warning' : 'success'" size="mini" @click="toggleStatus(scope.row)">{{ scope.row.status === 1 ? '撤回' : '发布' }}</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :visible.sync="dialogVisible" :title="editing ? '编辑公告' : '新增公告'" width="680px">
      <el-form ref="noticeForm" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="公告标题" prop="title">
          <el-input v-model="form.title" maxlength="100" show-word-limit placeholder="请输入公告标题" />
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input v-model="form.content" type="textarea" :rows="8" maxlength="5000" show-word-limit placeholder="请输入公告内容" />
        </el-form-item>
        <el-form-item label="发布状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="0">草稿</el-radio>
            <el-radio :label="1">立即发布</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="saving" @click="submitForm">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves'
import Pagination from '@/components/Pagination'
import { fetchNoticeList, createNotice, updateNotice, deleteNotice } from '@/api/notice'

function emptyForm() {
  return { id: null, title: '', content: '', status: 0 }
}

export default {
  name: 'NoticeManagement',
  components: { Pagination },
  directives: { waves },
  data() {
    return {
      list: [],
      total: 0,
      loading: false,
      saving: false,
      dialogVisible: false,
      editing: false,
      listQuery: { page: 1, limit: 10, title: '', status: undefined },
      form: emptyForm(),
      rules: {
        title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
        content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      fetchNoticeList(this.listQuery).then(response => {
        if (response.data.code === 20000) {
          this.list = response.data.data.items || []
          this.total = response.data.data.total || 0
        } else {
          this.$message.error(response.data.message || '获取公告失败')
        }
      }).catch(() => {
        this.$message.error('获取公告失败，请稍后重试')
      }).finally(() => {
        this.loading = false
      })
    },
    handleSearch() {
      this.listQuery.page = 1
      this.getList()
    },
    openCreate() {
      this.editing = false
      this.form = emptyForm()
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.noticeForm.clearValidate())
    },
    openEdit(row) {
      this.editing = true
      this.form = { id: row.id, title: row.title, content: row.content, status: row.status }
      this.dialogVisible = true
      this.$nextTick(() => this.$refs.noticeForm.clearValidate())
    },
    submitForm() {
      this.$refs.noticeForm.validate(valid => {
        if (!valid) return
        this.saving = true
        const request = this.editing ? updateNotice(this.form) : createNotice(this.form)
        request.then(response => {
          if (response.data.code === 20000) {
            this.$message.success(this.editing ? '公告已更新' : '公告已创建')
            this.dialogVisible = false
            this.getList()
          } else {
            this.$message.error(response.data.message || '保存公告失败')
          }
        }).catch(() => {
          this.$message.error('保存公告失败，请稍后重试')
        }).finally(() => {
          this.saving = false
        })
      })
    },
    toggleStatus(row) {
      const notice = { id: row.id, title: row.title, content: row.content, status: row.status === 1 ? 0 : 1 }
      updateNotice(notice).then(response => {
        if (response.data.code === 20000) {
          this.$message.success(notice.status === 1 ? '公告已发布' : '公告已撤回')
          this.getList()
        } else {
          this.$message.error(response.data.message || '更新公告状态失败')
        }
      }).catch(() => this.$message.error('更新公告状态失败，请稍后重试'))
    },
    handleDelete(row) {
      this.$confirm(`确定删除公告“${row.title}”吗？`, '删除确认', { type: 'warning' }).then(() => {
        deleteNotice(row.id).then(response => {
          if (response.data.code === 20000) {
            this.$message.success('公告已删除')
            this.getList()
          } else {
            this.$message.error(response.data.message || '删除公告失败')
          }
        }).catch(() => this.$message.error('删除公告失败，请稍后重试'))
      }).catch(() => {})
    }
  }
}
</script>
