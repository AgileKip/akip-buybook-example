<template>
  <div>
    <h2 class="jh-entity-heading" data-cy="buyBookBindingDetailsHeading">
      <span v-text="$t('buyBookApp.buyBookBinding.home.title')">BuyBookBinding</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('buyBookApp.buyBookBinding.home.refreshListLabel')">Refresh List</span>
        </button>

        <router-link :to="`/process-definition/procidBuyBook/init`" tag="button" class="btn btn-primary mr-2">
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('buyBookApp.buyBookBinding.home.createLabel')"> Create a new Travel Plan Process Instance </span>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && buyBookBindingList && buyBookBindingList.length === 0">
      <span v-text="$t('buyBookApp.buyBookBinding.home.notFound')">No buyBookBinding found</span>
    </div>
    <div class="table-responsive" v-if="buyBookBindingList && buyBookBindingList.length > 0">
      <table class="table table-striped" aria-describedby="buyBookBindingList">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span>Process Instance</span></th>
            <th scope="row"><span>Buy Book</span></th>
            <th scope="row"><span>Status</span></th>
            <th scope="row"><span>Start Date</span></th>
            <th scope="row"><span>End Date</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="buyBookBinding in buyBookBindingList" :key="buyBookBinding.id" data-cy="entityTable">
            <td>{{ buyBookBinding.id }}</td>
            <td>
              <div v-if="buyBookBinding.processInstance">
                <router-link :to="`/process-definition/procidBuyBook/instance/${buyBookBinding.processInstance.id}/view`">
                  {{ buyBookBinding.processInstance.businessKey }}
                </router-link>
              </div>
            </td>
            <td>
              <div v-if="buyBookBinding.buyBook">
                <router-link :to="{ name: 'BuyBookView', params: { buyBookId: buyBookBinding.buyBook.id } }">{{
                  buyBookBinding.buyBook.id
                }}</router-link>
              </div>
            </td>
            <td>
              <akip-show-process-instance-status :status="buyBookBinding.processInstance.status"></akip-show-process-instance-status>
            </td>
            <td>{{ buyBookBinding.processInstance.startDate ? $d(Date.parse(buyBookBinding.processInstance.startDate), 'short') : '' }}</td>
            <td>{{ buyBookBinding.processInstance.endDate ? $d(Date.parse(buyBookBinding.processInstance.endDate), 'short') : '' }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="`/process-definition/procidBuyBook/instance/${buyBookBinding.processInstance.id}/view`"
                  tag="button"
                  class="btn btn-info btn-sm details"
                  data-cy="entityDetailsButton"
                >
                  <font-awesome-icon icon="eye"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                </router-link>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
      <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
    </button>
  </div>
</template>

<script lang="ts" src="./buy-book-binding-list.component.ts"></script>
