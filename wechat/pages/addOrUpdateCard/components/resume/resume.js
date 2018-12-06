// pages/addOrUpdateCard/components/resume/resume.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    showModal: false,
    field: [],
    checkbox1: ['保险'],
    items: [
      { name: '保险', value: '保险' },
      { name: '税务', value: '税务'},
      { name: '争议解决', value: '争议解决' },
      { name: '刑事辩护', value: '刑事辩护' },
      { name: '婚姻家庭', value: '婚姻家庭' },
      { name: '继承遗产', value: '继承遗产' },
      { name: '劳动纠纷', value: '劳动纠纷' },
      { name: '交通事故', value: '交通事故' },
      { name: '公司收购', value: '公司收购' },
      { name: '海商海事', value: '海商海事' },
      { name: '知识产权', value: '知识产权' },
      { name: '兼并与重组', value: '兼并与重组' },
      { name: '银行与金融', value: '银行与金融' },
      { name: '证券与资本市场', value: '证券与资本市场' },
      { name: '外商投资与并购', value: '外商投资与并购' },
      { name: '房地产与建设工程', value: '房地产与建设工程' },
      { name: '私募股权与投资基金', value: '私募股权与投资基金' }
  ]

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  chooseImage() {
    const that = this
    wx.chooseImage({
      sourceType: ['album', 'camera'],
      sizeType: ['original', 'compressed'],
      count: 3,
      success(res) {
        console.log(res)
        that.setData({
          imageList: res.tempFilePaths
        })
      }
    })
  },
  /**
 * 弹窗
 */
  showDialogBtn: function () {
    this.setData({
      showModal: true
    })
  },
  /**
    * 隐藏模态对话框
    */
  hideModal: function () {
    this.setData({
      showModal: false
    });
  },
  /**
   * 对话框取消按钮点击事件
   */
  onCancel: function () {
    this.hideModal();
  },
  /**
   * 对话框确认按钮点击事件
   */
  onConfirm: function () {
    this.hideModal();
  },
  checkboxChange: function (e) {
    console.log('checkbox发生change事件，携带value值为：', e.detail.value)
    this.setData({
      field: e.detail.value
    });
  },
  formSubmit: function (e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    const data = e.detail.value
    data.field=this.data.field
    data.id = app.globalData.userInfo.id;
    var that = this;
    wx.request({
      url: app.globalData.baseService + "user/update",
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      method: "POST",
      data: data,
      //data: { code: code},
      complete: function (res) {
        console.log(res)
        app.globalData.userInfo = res.data.data
        console.log(app.globalData.userInfo);
        wx.navigateTo({
          url: '/pages/addOrUpdateCard/components/case/case'
        })
      }
    })

  }
})