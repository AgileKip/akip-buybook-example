<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('buyBookApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label class="form-control-label" v-text="$t('buyBookApp.taskPayBook.purpose')" for="task-pay-book-purpose">Purpose</label>
              <input
                type="text"
                class="form-control"
                name="purpose"
                id="task-pay-book-purpose"
                readonly
                data-cy="purpose"
                :class="{
                  valid: !$v.taskContext.buyBookBinding.buyBook.purpose.$invalid,
                  invalid: $v.taskContext.buyBookBinding.buyBook.purpose.$invalid,
                }"
                v-model="$v.taskContext.buyBookBinding.buyBook.purpose.$model"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('buyBookApp.taskPayBook.paymentDetails')" for="task-pay-book-paymentDetails"
                >Payment Details</label
              >
              <input
                type="text"
                class="form-control"
                name="paymentDetails"
                id="task-pay-book-paymentDetails"
                data-cy="paymentDetails"
                :class="{
                  valid: !$v.taskContext.buyBookBinding.buyBook.paymentDetails.$invalid,
                  invalid: $v.taskContext.buyBookBinding.buyBook.paymentDetails.$invalid,
                }"
                v-model="$v.taskContext.buyBookBinding.buyBook.paymentDetails.$model"
                required
              />
              <div
                v-if="
                  $v.taskContext.buyBookBinding.buyBook.paymentDetails.$anyDirty &&
                  $v.taskContext.buyBookBinding.buyBook.paymentDetails.$invalid
                "
              >
                <small
                  class="form-text text-danger"
                  v-if="!$v.taskContext.buyBookBinding.buyBook.paymentDetails.required"
                  v-text="$t('entity.validation.required')"
                >
                  This field is required.
                </small>
              </div>
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <button type="submit" v-on:click.prevent="complete()" class="btn btn-success" data-cy="complete">
          <font-awesome-icon icon="check-circle"></font-awesome-icon>&nbsp;Complete
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-pay-book-execute.component.ts"></script>
