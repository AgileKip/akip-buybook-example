import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const BuyBook = () => import('@/entities/buy-book/buy-book.vue');
// prettier-ignore
const BuyBookUpdate = () => import('@/entities/buy-book/buy-book-update.vue');
// prettier-ignore
const BuyBookDetails = () => import('@/entities/buy-book/buy-book-details.vue');
// prettier-ignore
const StartFormInit = () => import('@/entities/buy-book-binding/start-form-init.vue');
// prettier-ignore
const BuyBookBindingDetails = () => import('@/entities/buy-book-binding/buy-book-binding-details.vue');
// prettier-ignore
const BuyBookBindingList = () => import('@/entities/buy-book-binding/buy-book-binding-list.vue');
// prettier-ignore
const BuyBookBinding_TaskSelectBookDetails = () => import('@/entities/buy-book-binding/task-select-book/task-select-book-details.vue');
// prettier-ignore
const BuyBookBinding_TaskSelectBookExecute = () => import('@/entities/buy-book-binding/task-select-book/task-select-book-execute.vue');
// prettier-ignore
const BuyBookBinding_TaskPayBookDetails = () => import('@/entities/buy-book-binding/task-pay-book/task-pay-book-details.vue');
// prettier-ignore
const BuyBookBinding_TaskPayBookExecute = () => import('@/entities/buy-book-binding/task-pay-book/task-pay-book-execute.vue');
// prettier-ignore
const BuyBookBinding_TaskHandleOrderDetails = () => import('@/entities/buy-book-binding/task-handle-order/task-handle-order-details.vue');
// prettier-ignore
const BuyBookBinding_TaskHandleOrderExecute = () => import('@/entities/buy-book-binding/task-handle-order/task-handle-order-execute.vue');
// prettier-ignore
const BuyBookBinding_TaskSelectPickUpStoreDetails = () => import('@/entities/buy-book-binding/task-select-pick-up-store/task-select-pick-up-store-details.vue');
// prettier-ignore
const BuyBookBinding_TaskSelectPickUpStoreExecute = () => import('@/entities/buy-book-binding/task-select-pick-up-store/task-select-pick-up-store-execute.vue');
// prettier-ignore
const BuyBookBinding_TaskAddShippingInfoDetails = () => import('@/entities/buy-book-binding/task-add-shipping-info/task-add-shipping-info-details.vue');
// prettier-ignore
const BuyBookBinding_TaskAddShippingInfoExecute = () => import('@/entities/buy-book-binding/task-add-shipping-info/task-add-shipping-info-execute.vue');
// prettier-ignore
const BuyBookBinding_TaskSelectDeliveryDetails = () => import('@/entities/buy-book-binding/task-select-delivery/task-select-delivery-details.vue');
// prettier-ignore
const BuyBookBinding_TaskSelectDeliveryExecute = () => import('@/entities/buy-book-binding/task-select-delivery/task-select-delivery-execute.vue');
// prettier-ignore
const Book = () => import('@/entities/book/book.vue');
// prettier-ignore
const BookUpdate = () => import('@/entities/book/book-update.vue');
// prettier-ignore
const BookDetails = () => import('@/entities/book/book-details.vue');
// prettier-ignore
const Publisher = () => import('@/entities/publisher/publisher.vue');
// prettier-ignore
const PublisherUpdate = () => import('@/entities/publisher/publisher-update.vue');
// prettier-ignore
const PublisherDetails = () => import('@/entities/publisher/publisher-details.vue');
// prettier-ignore
const Store = () => import('@/entities/store/store.vue');
// prettier-ignore
const StoreUpdate = () => import('@/entities/store/store-update.vue');
// prettier-ignore
const StoreDetails = () => import('@/entities/store/store-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/buy-book',
    name: 'BuyBook',
    component: BuyBook,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/buy-book/new',
    name: 'BuyBookCreate',
    component: BuyBookUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/buy-book/:buyBookId/edit',
    name: 'BuyBookEdit',
    component: BuyBookUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/buy-book/:buyBookId/view',
    name: 'BuyBookView',
    component: BuyBookDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/init',
    name: 'StartFormInit',
    component: StartFormInit,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/instance/:processInstanceId/view',
    name: 'BuyBookBindingView',
    component: BuyBookBindingDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/instances',
    name: 'BuyBookBindingList',
    component: BuyBookBindingList,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskSelectBook/:taskInstanceId/view',
    name: 'BuyBookBinding_TaskSelectBookDetails',
    component: BuyBookBinding_TaskSelectBookDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskSelectBook/:taskInstanceId/execute',
    name: 'BuyBookBinding_TaskSelectBookExecute',
    component: BuyBookBinding_TaskSelectBookExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskPayBook/:taskInstanceId/view',
    name: 'BuyBookBinding_TaskPayBookDetails',
    component: BuyBookBinding_TaskPayBookDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskPayBook/:taskInstanceId/execute',
    name: 'BuyBookBinding_TaskPayBookExecute',
    component: BuyBookBinding_TaskPayBookExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskHandleOrder/:taskInstanceId/view',
    name: 'BuyBookBinding_TaskHandleOrderDetails',
    component: BuyBookBinding_TaskHandleOrderDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskHandleOrder/:taskInstanceId/execute',
    name: 'BuyBookBinding_TaskHandleOrderExecute',
    component: BuyBookBinding_TaskHandleOrderExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskSelectPickUpStore/:taskInstanceId/view',
    name: 'BuyBookBinding_TaskSelectPickUpStoreDetails',
    component: BuyBookBinding_TaskSelectPickUpStoreDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskSelectPickUpStore/:taskInstanceId/execute',
    name: 'BuyBookBinding_TaskSelectPickUpStoreExecute',
    component: BuyBookBinding_TaskSelectPickUpStoreExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskAddShippingInfo/:taskInstanceId/view',
    name: 'BuyBookBinding_TaskAddShippingInfoDetails',
    component: BuyBookBinding_TaskAddShippingInfoDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskAddShippingInfo/:taskInstanceId/execute',
    name: 'BuyBookBinding_TaskAddShippingInfoExecute',
    component: BuyBookBinding_TaskAddShippingInfoExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskSelectDelivery/:taskInstanceId/view',
    name: 'BuyBookBinding_TaskSelectDeliveryDetails',
    component: BuyBookBinding_TaskSelectDeliveryDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/process-definition/procidBuyBook/task/TaskSelectDelivery/:taskInstanceId/execute',
    name: 'BuyBookBinding_TaskSelectDeliveryExecute',
    component: BuyBookBinding_TaskSelectDeliveryExecute,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/book',
    name: 'Book',
    component: Book,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/book/new',
    name: 'BookCreate',
    component: BookUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/book/:bookId/edit',
    name: 'BookEdit',
    component: BookUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/book/:bookId/view',
    name: 'BookView',
    component: BookDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/publisher',
    name: 'Publisher',
    component: Publisher,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/publisher/new',
    name: 'PublisherCreate',
    component: PublisherUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/publisher/:publisherId/edit',
    name: 'PublisherEdit',
    component: PublisherUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/publisher/:publisherId/view',
    name: 'PublisherView',
    component: PublisherDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/store',
    name: 'Store',
    component: Store,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/store/new',
    name: 'StoreCreate',
    component: StoreUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/store/:storeId/edit',
    name: 'StoreEdit',
    component: StoreUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/store/:storeId/view',
    name: 'StoreView',
    component: StoreDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
