<template>
  <v-sheet class="d-flex flex-column justify-center mt-12">
    <div class="d-flex justify-center relative">
      <v-card width="350" class="card-border px-3 pt-3 pb-5">
        <v-form ref="loginForm" v-model="valid" @submit.prevent>
          <v-card-title class="font-weight-bold justify-center">
            로그인
          </v-card-title>
          <v-card-text class="px-4 pt-4 pb-0">
            <div class="d-flex">
              <v-text-field
                color="grey darken-1"
                label="이메일을 입력해주세요."
                v-model="member.email"
                prepend-inner-icon="mdi-email"
                dense
                outlined
                :rules="rules.member.email"
              ></v-text-field>
            </div>
            <div class="d-flex mt-2">
              <v-text-field
                color="grey darken-1"
                label="비밀번호를 입력해주세요."
                v-model="member.password"
                prepend-inner-icon="mdi-lock"
                dense
                outlined
                type="password"
                :rules="rules.member.password"
              ></v-text-field>
            </div>
          </v-card-text>
          <v-card-actions class="px-4 pb-4">
            <v-spacer></v-spacer>
            <v-btn
              @click.prevent="onLogin"
              :disabled="!valid"
              color="amber"
              class="w-100"
              depressed
            >
              로그인
            </v-btn>
          </v-card-actions>
          <router-link to="join" class="d-flex justify-center">
            <span>아직 회원이 아니신가요?</span>
          </router-link>
        </v-form>
      </v-card>
    </div>
  </v-sheet>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";
import { SET_MEMBER, SHOW_SNACKBAR, SET_ACCESS_TOKEN } from "../../store/shared/mutationTypes";
import { SNACKBAR_MESSAGES } from "../../utils/constants";
import validator from "../../utils/validator";

export default {
  name: "LoginPage",
  computed: {
    ...mapGetters(["accessToken"]),
  },
  methods: {
    ...mapMutations([SHOW_SNACKBAR, SET_MEMBER, SET_ACCESS_TOKEN]),
    isValid() {
      return this.$refs.loginForm.validate();
    },
    async onLogin() {
      if (!this.isValid()) {
        return;
      }
      try {
        // login API를 작성해주세요.
        const { email, password } = this.member;

        const data = await fetch("http://localhost:8080/login/token", {
          method: "POST",
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
          },
          body: JSON.stringify({
            email: email,
            password: password,
          }),
        });

        const body = await data.json();
        localStorage.setItem("token", body.accessToken);

        // member 데이터를 불러와 주세요.
        const response = await fetch("http://localhost:8080/members/me", {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${localStorage.getItem("token")}`
          }
        });
        const memberData = await response.json();
        this.setMember(memberData);
        await this.$router.replace(`/`);
        this.showSnackbar(SNACKBAR_MESSAGES.LOGIN.SUCCESS);
      } catch (e) {
        this.showSnackbar(SNACKBAR_MESSAGES.LOGIN.FAIL);
        throw new Error(e);
      }
    },
  },
  data() {
    return {
      valid: false,
      rules: { ...validator },
      member: {
        email: "",
        password: "",
      },
    };
  },
};
</script>
