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
              <label class="form-control-label" v-text="$t('buyBookApp.taskSelectBook.purpose')" for="task-select-book-purpose"
                >Purpose</label
              >
              <input
                type="text"
                class="form-control"
                name="purpose"
                id="task-select-book-purpose"
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
              <label class="form-control-label" v-text="$t('buyBookApp.taskSelectBook.book')" for="task-select-book-book">Book</label>
              <select
                class="form-control"
                id="task-select-book-book"
                data-cy="book"
                name="book"
                v-model="taskContext.buyBookBinding.buyBook.book"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.buyBookBinding.buyBook.book && bookOption.id === taskContext.buyBookBinding.buyBook.book.id
                      ? taskContext.buyBookBinding.buyBook.book
                      : bookOption
                  "
                  v-for="bookOption in books"
                  :key="bookOption.id"
                >
                  {{ bookOption.name }}
                </option>
              </select>
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

<script lang="ts" src="./task-select-book-execute.component.ts"></script>
